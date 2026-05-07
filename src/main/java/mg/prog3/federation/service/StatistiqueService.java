package mg.prog3.federation.service;

import lombok.RequiredArgsConstructor;
import mg.prog3.federation.dto.response.CollectiviteStatistiqueResponse;
import mg.prog3.federation.dto.response.GlobalStatistiqueResponse;
import mg.prog3.federation.dto.response.MembreStatistiqueResponse;
import mg.prog3.federation.entity.Collectivite;
import mg.prog3.federation.entity.Cotisation;
import mg.prog3.federation.entity.Membre;
import mg.prog3.federation.exception.BusinessException;
import mg.prog3.federation.exception.ResourceNotFoundException;
import mg.prog3.federation.repository.CollectiviteRepository;
import mg.prog3.federation.repository.CotisationRepository;
import mg.prog3.federation.repository.MembreRepository;
import mg.prog3.federation.repository.PaiementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatistiqueService {

    private final CollectiviteRepository collectiviteRepository;
    private final MembreRepository membreRepository;
    private final CotisationRepository cotisationRepository;
    private final PaiementRepository paiementRepository;

    @Transactional(readOnly = true)
    public CollectiviteStatistiqueResponse getStatistiquesCollectivite(Long collectiviteId, LocalDate debut, LocalDate fin) {
        Collectivite collectivite = collectiviteRepository.findById(collectiviteId)
                .orElseThrow(() -> new ResourceNotFoundException("Collectivite non trouvée: " + collectiviteId));

        if (debut.isAfter(fin)) {
            throw new BusinessException("La date de début doit être antérieure à la date de fin");
        }

        List<MembreStatistiqueResponse> membresStats = new ArrayList<>();

        List<Membre> membresActifs = membreRepository.findByCollectiviteId(collectiviteId)
                .stream()
                .filter(Membre::isActif)
                .toList();

        for (Membre membre : membresActifs) {
            Long montantEncaissement = paiementRepository.sumPaiementsByMembreAndPeriode(
                    membre.getId(), debut, fin);

            Long montantImpaye = calculerMontantImpaye(collectiviteId, membre.getId(), debut, fin);

            membresStats.add(MembreStatistiqueResponse.builder()
                    .membreId(membre.getId())
                    .membreNom(membre.getNom())
                    .membrePrenom(membre.getPrenom())
                    .montantEncaissement(montantEncaissement)
                    .montantImpaye(montantImpaye)
                    .build());
        }

        return CollectiviteStatistiqueResponse.builder()
                .collectiviteId(collectivite.getId())
                .collectiviteNom(collectivite.getNom())
                .membres(membresStats)
                .build();
    }

    @Transactional(readOnly = true)
    public List<GlobalStatistiqueResponse> getStatistiquesGlobales(LocalDate debut, LocalDate fin) {
        if (debut.isAfter(fin)) {
            throw new BusinessException("La date de début doit être antérieure à la date de fin");
        }

        List<Collectivite> collectivites = collectiviteRepository.findAll();
        List<GlobalStatistiqueResponse> statsGlobales = new ArrayList<>();

        for (Collectivite collectivite : collectivites) {
            double pourcentageAJour = calculerPourcentageMembresAJour(collectivite.getId(), debut, fin);
            long nouveauxAdherents = paiementRepository.countNouveauxAdherents(collectivite.getId(), debut, fin);

            statsGlobales.add(GlobalStatistiqueResponse.builder()
                    .collectiviteId(collectivite.getId())
                    .collectiviteNom(collectivite.getNom())
                    .pourcentageMembresAJour(pourcentageAJour)
                    .nombreNouveauxAdherents(nouveauxAdherents)
                    .build());
        }

        return statsGlobales;
    }

    private Long calculerMontantImpaye(Long collectiviteId, Long membreId, LocalDate debut, LocalDate fin) {
        List<Cotisation> cotisationsActives = cotisationRepository.findByCollectiviteIdAndActive(collectiviteId, true);

        long totalCotisationsActives = cotisationsActives.stream()
                .mapToLong(Cotisation::getMontant)
                .sum();

        long totalPaye = paiementRepository.sumPaiementsByMembreAndPeriode(membreId, debut, fin);

        return Math.max(0, totalCotisationsActives - totalPaye);
    }

    private double calculerPourcentageMembresAJour(Long collectiviteId, LocalDate debut, LocalDate fin) {
        List<Membre> membresActifs = membreRepository.findByCollectiviteId(collectiviteId)
                .stream()
                .filter(Membre::isActif)
                .toList();

        if (membresActifs.isEmpty()) {
            return 0.0;
        }

        List<Cotisation> cotisationsActives = cotisationRepository.findByCollectiviteIdAndActive(collectiviteId, true);

        long totalCotisations = cotisationsActives.stream()
                .mapToLong(Cotisation::getMontant)
                .sum();

        if (totalCotisations == 0) {
            return 100.0;
        }

        long membresAJour = 0;
        for (Membre membre : membresActifs) {
            long totalPaye = paiementRepository.sumPaiementsByMembreAndPeriode(
                    membre.getId(), debut, fin);

            if (totalPaye >= (totalCotisations * 0.8)) {
                membresAJour++;
            }
        }

        return (double) membresAJour / membresActifs.size() * 100.0;
    }
}