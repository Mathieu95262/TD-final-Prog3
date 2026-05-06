package mg.prog3.federation.service;

import lombok.RequiredArgsConstructor;
import mg.prog3.federation.dto.request.AssignInformationsRequest;
import mg.prog3.federation.dto.request.CreateCollectiviteRequest;
import mg.prog3.federation.dto.request.MembreCollectiviteRequest;
import mg.prog3.federation.dto.response.CollectiviteResponse;
import mg.prog3.federation.dto.response.MembreResponse;
import mg.prog3.federation.entity.Collectivite;
import mg.prog3.federation.entity.Membre;
import mg.prog3.federation.enums.Poste;
import mg.prog3.federation.exception.BusinessException;
import mg.prog3.federation.exception.ConflictException;
import mg.prog3.federation.exception.ResourceNotFoundException;
import mg.prog3.federation.repository.CollectiviteRepository;
import mg.prog3.federation.repository.MembreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectiviteService {

    private final CollectiviteRepository collectiviteRepository;
    private final MembreRepository membreRepository;

    @Transactional
    public Collection<CollectiviteResponse> creerCollectivites(Collection<CreateCollectiviteRequest> requests) {
        return requests.stream().map(this::creerUneCollectivite).toList();
    }

    private CollectiviteResponse creerUneCollectivite(CreateCollectiviteRequest request) {
        Collection<MembreCollectiviteRequest> membresRequest = request.getMembres();

        if (membresRequest.size() < 10) {
            throw new BusinessException("A collectivite must have at least 10 registered members.");
        }

        LocalDate seniorityThreshold = LocalDate.now().minusMonths(6);
        long membersWithSeniority = membresRequest.stream()
                .filter(m -> m.getDateAdhesionFederation() != null
                        && !m.getDateAdhesionFederation().isAfter(seniorityThreshold))
                .count();

        if (membersWithSeniority < 5) {
            throw new BusinessException(
                    "At least 5 members must have 6 months of seniority. Eligible: "
                            + membersWithSeniority);
        }

        validateRequiredPositions(membresRequest);

        for (MembreCollectiviteRequest mr : membresRequest) {
            if (membreRepository.existsByEmail(mr.getEmail())) {
                throw new ConflictException("Email already in use: " + mr.getEmail());
            }
        }

        Collectivite collectivite = Collectivite.builder()
                .ville(request.getVille())
                .specialiteAgricole(request.getSpecialiteAgricole())
                .dateCreation(LocalDate.now())
                .autorisationOuverture(true)
                .cotisationAnnuelleObligatoire(request.getCotisationAnnuelleObligatoire())
                .build();

        collectivite = collectiviteRepository.save(collectivite);

        List<Membre> membres = new ArrayList<>();
        for (MembreCollectiviteRequest mr : membresRequest) {
            Membre membre = Membre.builder()
                    .nom(mr.getNom())
                    .prenom(mr.getPrenom())
                    .dateNaissance(mr.getDateNaissance())
                    .genre(mr.getGenre())
                    .adresse(mr.getAdresse())
                    .metier(mr.getMetier())
                    .telephone(mr.getTelephone())
                    .email(mr.getEmail())
                    .dateAdhesion(mr.getDateAdhesionFederation() != null
                            ? mr.getDateAdhesionFederation() : LocalDate.now())
                    .poste(mr.getPoste())
                    .actif(true)
                    .collectiviteId(collectivite.getId())
                    .build();
            membres.add(membre);
        }
        membreRepository.saveAll(membres);

        return toResponse(collectivite, membres.size(), null);
    }

    @Transactional
    public CollectiviteResponse assignerInformations(Long id, AssignInformationsRequest request) {
        Collectivite collectivite = findById(id);

        if (collectivite.getNumero() != null) {
            throw new ConflictException("Number has already been assigned and cannot be changed.");
        }
        if (collectivite.getNom() != null) {
            throw new ConflictException("Name has already been assigned and cannot be changed.");
        }
        if (collectiviteRepository.existsByNumero(request.getNumero())) {
            throw new ConflictException("Number '" + request.getNumero() + "' is already in use.");
        }
        if (collectiviteRepository.existsByNom(request.getNom())) {
            throw new ConflictException("Name '" + request.getNom() + "' is already in use.");
        }

        collectivite.setNumero(request.getNumero());
        collectivite.setNom(request.getNom());
        collectiviteRepository.save(collectivite);

        long count = membreRepository.countMembresActifs(id);
        return toResponse(collectivite, count, null);
    }

    @Transactional(readOnly = true)
    public CollectiviteResponse getCollectivite(Long id) {
        Collectivite collectivite = findById(id);
        long count = membreRepository.countMembresActifs(id);

        Collection<MembreResponse> members = membreRepository.findByCollectiviteId(id).stream()
                .map(this::toMembreResponse)
                .toList();

        return toResponse(collectivite, count, members);
    }

    @Transactional(readOnly = true)
    public Collection<CollectiviteResponse> getAllCollectivites() {
        return collectiviteRepository.findAll().stream()
                .map(c -> toResponse(c, membreRepository.countMembresActifs(c.getId()), null))
                .toList();
    }

    private Collectivite findById(Long id) {
        return collectiviteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collectivite not found: " + id));
    }

    private void validateRequiredPositions(Collection<MembreCollectiviteRequest> membres) {
        long nbPresident = membres.stream().filter(m -> m.getPoste() == Poste.PRESIDENT).count();
        long nbVicePresident = membres.stream().filter(m -> m.getPoste() == Poste.VICE_PRESIDENT).count();
        long nbTreasurer = membres.stream().filter(m -> m.getPoste() == Poste.TREASURER).count();
        long nbSecretary = membres.stream().filter(m -> m.getPoste() == Poste.SECRETARY).count();

        if (nbPresident == 0)       throw new BusinessException("President position is required.");
        if (nbPresident > 1)        throw new BusinessException("Only one president allowed.");
        if (nbVicePresident == 0)   throw new BusinessException("Vice-president position is required.");
        if (nbVicePresident > 1)    throw new BusinessException("Only one vice-president allowed.");
        if (nbTreasurer == 0)       throw new BusinessException("Treasurer position is required.");
        if (nbTreasurer > 1)        throw new BusinessException("Only one treasurer allowed.");
        if (nbSecretary == 0)       throw new BusinessException("Secretary position is required.");
        if (nbSecretary > 1)        throw new BusinessException("Only one secretary allowed.");
    }

    private CollectiviteResponse toResponse(Collectivite c, long count, Collection<MembreResponse> members) {
        return CollectiviteResponse.builder()
                .id(c.getId())
                .numero(c.getNumero())
                .nom(c.getNom())
                .ville(c.getVille())
                .specialiteAgricole(c.getSpecialiteAgricole())
                .dateCreation(c.getDateCreation())
                .autorisationOuverture(c.isAutorisationOuverture())
                .cotisationAnnuelleObligatoire(c.getCotisationAnnuelleObligatoire())
                .nombreMembres(count)
                .members(members)
                .build();
    }

    private MembreResponse toMembreResponse(Membre m) {
        String collectiviteNom = collectiviteRepository.findById(m.getCollectiviteId())
                .map(Collectivite::getNom)
                .orElse(null);

        return MembreResponse.builder()
                .id(m.getId())
                .nom(m.getNom())
                .prenom(m.getPrenom())
                .dateNaissance(m.getDateNaissance())
                .genre(m.getGenre())
                .adresse(m.getAdresse())
                .metier(m.getMetier())
                .telephone(m.getTelephone())
                .email(m.getEmail())
                .dateAdhesion(m.getDateAdhesion())
                .poste(m.getPoste())
                .actif(m.isActif())
                .collectiviteId(m.getCollectiviteId())
                .collectiviteNom(collectiviteNom)
                .build();
    }
}