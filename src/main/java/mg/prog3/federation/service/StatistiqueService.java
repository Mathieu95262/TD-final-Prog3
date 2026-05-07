package mg.prog3.federation.service;

import lombok.RequiredArgsConstructor;
import mg.prog3.federation.dto.response.CollectiviteStatistiqueResponse;
import mg.prog3.federation.dto.response.MembreStatistiqueResponse;
import mg.prog3.federation.entity.*;
import mg.prog3.federation.repository.*;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StatistiqueService {

    private final MembreRepository membreRepository;
    private final CotisationRepository cotisationRepository;
    private final PaiementRepository paiementRepository;
    private final CollectiviteRepository collectiviteRepository;
    private final PresenceRepository presenceRepository;
    private final ActiviteRepository activiteRepository;

    // G - Stats par membre (modifié avec assiduité)
    public List<MembreStatistiqueResponse> getStatistiquesCollectivite(Long collectiviteId, LocalDate debut, LocalDate fin) {
        List<Membre> membres = membreRepository.findByCollectiviteId(collectiviteId);
        List<Cotisation> cotisationsActives = cotisationRepository.findByCollectiviteIdAndActive(collectiviteId, true);

        Date dateDebut = Date.valueOf(debut);
        Date dateFin = Date.valueOf(fin);

        long totalActivites = presenceRepository.countTotalActivitesByCollectiviteAndPeriode(collectiviteId, dateDebut, dateFin);

        List<MembreStatistiqueResponse> stats = new ArrayList<>();

        for (Membre membre : membres) {
            // Montant encaissé
            List<Paiement> paiements = paiementRepository.findByMembreId(membre.getId());
            long montantEncaissement = paiements.stream()
                    .filter(p -> !p.getDateEncaissement().isBefore(debut) && !p.getDateEncaissement().isAfter(fin))
                    .mapToLong(Paiement::getMontant)
                    .sum();

            // Montant impayé
            long totalCotisationsActives = cotisationsActives.stream().mapToLong(Cotisation::getMontant).sum();
            long totalPaye = paiements.stream()
                    .filter(p -> cotisationsActives.stream().anyMatch(c -> c.getId().equals(p.getCotisationId())))
                    .mapToLong(Paiement::getMontant)
                    .sum();
            long montantImpaye = Math.max(0, totalCotisationsActives - totalPaye);

            // Taux d'assiduité
            long nbPresences = presenceRepository.countPresencesByMembreIdAndPeriode(membre.getId(), dateDebut, dateFin);
            double tauxAssiduite = totalActivites > 0 ? (double) nbPresences / totalActivites * 100 : 0.0;
            tauxAssiduite = Math.round(tauxAssiduite * 100.0) / 100.0;

            stats.add(MembreStatistiqueResponse.builder()
                    .membreId(membre.getId())
                    .membreNom(membre.getNom())
                    .membrePrenom(membre.getPrenom())
                    .earnedAmount(montantEncaissement)
                    .unpaidAmount(montantImpaye)
                    .assiduityPercentage(tauxAssiduite)
                    .build());
        }
        return stats;
    }

    // H - Stats globales (modifié avec assiduité)
    public List<CollectiviteStatistiqueResponse> getStatistiquesGlobales(LocalDate debut, LocalDate fin) {
        List<mg.prog3.federation.entity.Collectivite> collectivites = collectiviteRepository.findAll();
        Date dateDebut = Date.valueOf(debut);
        Date dateFin = Date.valueOf(fin);

        List<CollectiviteStatistiqueResponse> stats = new ArrayList<>();

        for (mg.prog3.federation.entity.Collectivite c : collectivites) {
            List<Membre> membres = membreRepository.findByCollectiviteId(c.getId());
            List<Cotisation> cotisationsActives = cotisationRepository.findByCollectiviteIdAndActive(c.getId(), true);

            // Pourcentage membres à jour
            long membresAJour = membres.stream()
                    .filter(m -> {
                        List<Paiement> paiements = paiementRepository.findByMembreId(m.getId());
                        long totalPaye = paiements.stream()
                                .filter(p -> cotisationsActives.stream().anyMatch(co -> co.getId().equals(p.getCotisationId())))
                                .mapToLong(Paiement::getMontant)
                                .sum();
                        long totalDu = cotisationsActives.stream().mapToLong(Cotisation::getMontant).sum();
                        return totalDu == 0 || totalPaye >= totalDu;
                    })
                    .count();
            double pourcentage = membres.isEmpty() ? 0 : (double) membresAJour / membres.size() * 100;

            // Nouveaux adhérents
            long nouveaux = membres.stream()
                    .filter(m -> !m.getDateAdhesion().isBefore(debut) && !m.getDateAdhesion().isAfter(fin))
                    .count();

            // Taux d'assiduité global
            long totalPresences = 0;
            long totalActivites = presenceRepository.countTotalActivitesByCollectiviteAndPeriode(c.getId(), dateDebut, dateFin);
            for (Membre m : membres) {
                totalPresences += presenceRepository.countPresencesByMembreIdAndPeriode(m.getId(), dateDebut, dateFin);
            }
            double tauxAssiduiteGlobal = totalActivites > 0 && !membres.isEmpty()
                    ? (double) totalPresences / (totalActivites * membres.size()) * 100 : 0.0;
            tauxAssiduiteGlobal = Math.round(tauxAssiduiteGlobal * 100.0) / 100.0;

            stats.add(CollectiviteStatistiqueResponse.builder()
                    .collectiviteId(c.getId())
                    .collectiviteNom(c.getNom())
                    .newMembersNumber(nouveaux)
                    .overallMemberCurrentDuePercentage(Math.round(pourcentage * 100.0) / 100.0)
                    .overallMemberAssiduityPercentage(tauxAssiduiteGlobal)
                    .build());
        }
        return stats;
    }
}