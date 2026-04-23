package mg.prog3.federation.service;

import lombok.RequiredArgsConstructor;
import mg.prog3.federation.dto.request.CreateMembreRequest;
import mg.prog3.federation.dto.request.CreatePaiementRequest;
import mg.prog3.federation.dto.request.ParrainRequest;
import mg.prog3.federation.dto.response.MembreResponse;
import mg.prog3.federation.dto.response.PaiementResponse;
import mg.prog3.federation.entity.Collectivite;
import mg.prog3.federation.entity.Cotisation;
import mg.prog3.federation.entity.Membre;
import mg.prog3.federation.entity.Paiement;
import mg.prog3.federation.enums.Poste;
import mg.prog3.federation.exception.BusinessException;
import mg.prog3.federation.exception.ConflictException;
import mg.prog3.federation.exception.ResourceNotFoundException;
import mg.prog3.federation.repository.CollectiviteRepository;
import mg.prog3.federation.repository.CotisationRepository;
import mg.prog3.federation.repository.MembreRepository;
import mg.prog3.federation.repository.PaiementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MembreService {

    private final MembreRepository membreRepository;
    private final CollectiviteRepository collectiviteRepository;
    private final PaiementRepository paiementRepository;
    private final CotisationRepository cotisationRepository;

    @Transactional
    public List<MembreResponse> admettreMembers(List<CreateMembreRequest> requests) {
        return requests.stream().map(this::admettreMembre).toList();
    }

    private MembreResponse admettreMembre(CreateMembreRequest request) {
        Collectivite collectivite = collectiviteRepository.findById(request.getCollectiviteId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Collectivite not found: " + request.getCollectiviteId()));

        if (membreRepository.existsByEmail(request.getEmail())) {
            throw new ConflictException("Email already in use: " + request.getEmail());
        }

        List<ParrainRequest> parrainRequests = request.getParrains();

        if (parrainRequests == null || parrainRequests.size() < 2) {
            throw new BusinessException("At least 2 confirmed sponsors are required.");
        }

        LocalDate threshold90days = LocalDate.now().minusDays(90);
        List<Membre> sponsors = parrainRequests.stream()
                .map(pr -> {
                    Membre sponsor = membreRepository.findById(pr.getParrainId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "Sponsor not found: " + pr.getParrainId()));
                    if (sponsor.getPoste() != Poste.CONFIRMED_MEMBER) {
                        throw new BusinessException(
                                "Sponsor " + sponsor.getNom() + " is not a confirmed member.");
                    }
                    if (sponsor.getDateAdhesion().isAfter(threshold90days)) {
                        throw new BusinessException(
                                "Sponsor " + sponsor.getNom() + " does not have 90 days of seniority.");
                    }
                    return sponsor;
                })
                .toList();

        long fromSameCollectivite = sponsors.stream()
                .filter(p -> p.getCollectivite().getId().equals(request.getCollectiviteId()))
                .count();
        long external = sponsors.size() - fromSameCollectivite;

        if (fromSameCollectivite < external) {
            throw new BusinessException(
                    "Sponsors from target collectivite (" + fromSameCollectivite
                    + ") must be >= external sponsors (" + external + ").");
        }

        long admissionFee = 50_000L;
        long annualFee = collectivite.getCotisationAnnuelleObligatoire() != null
                ? collectivite.getCotisationAnnuelleObligatoire() : 0L;
        long expectedAmount = admissionFee + annualFee;

        if (request.getMontantPaye() < expectedAmount) {
            throw new BusinessException(
                    "Insufficient amount. Expected: " + expectedAmount
                    + " MGA. Received: " + request.getMontantPaye() + " MGA.");
        }

        String paymentMethod = request.getModePaiement();
        if (!"BANK_TRANSFER".equalsIgnoreCase(paymentMethod)
                && !"MOBILE_MONEY".equalsIgnoreCase(paymentMethod)) {
            throw new BusinessException(
                    "Payment method must be BANK_TRANSFER or MOBILE_MONEY.");
        }

        Membre membre = Membre.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .dateNaissance(request.getDateNaissance())
                .genre(request.getGenre())
                .adresse(request.getAdresse())
                .metier(request.getMetier())
                .telephone(request.getTelephone())
                .email(request.getEmail())
                .dateAdhesion(LocalDate.now())
                .poste(Poste.JUNIOR_MEMBER)
                .actif(true)
                .collectivite(collectivite)
                .build();

        return toResponse(membreRepository.save(membre));
    }

    @Transactional
    public List<PaiementResponse> enregistrerPaiements(Long membreId, List<CreatePaiementRequest> requests) {
        Membre membre = findById(membreId);
        return requests.stream()
                .map(req -> enregistrerUnPaiement(membre, req))
                .toList();
    }

    private PaiementResponse enregistrerUnPaiement(Membre membre, CreatePaiementRequest req) {
        Cotisation cotisation = cotisationRepository.findById(req.getCotisationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Membership fee not found: " + req.getCotisationId()));

        if (!cotisation.getCollectivite().getId().equals(membre.getCollectivite().getId())) {
            throw new BusinessException("Membership fee does not belong to the member's collectivite.");
        }

        Paiement paiement = Paiement.builder()
                .montant(req.getMontant())
                .dateEncaissement(req.getDateEncaissement())
                .modePaiement(req.getModePaiement())
                .membre(membre)
                .cotisation(cotisation)
                .build();

        return toPaiementResponse(paiementRepository.save(paiement));
    }

    @Transactional(readOnly = true)
    public MembreResponse getMembre(Long id) {
        return toResponse(findById(id));
    }

    @Transactional(readOnly = true)
    public List<MembreResponse> getMembresByCollectivite(Long collectiviteId) {
        if (!collectiviteRepository.existsById(collectiviteId)) {
            throw new ResourceNotFoundException("Collectivite not found: " + collectiviteId);
        }
        return membreRepository.findByCollectiviteId(collectiviteId)
                .stream().map(this::toResponse).toList();
    }

    private Membre findById(Long id) {
        return membreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + id));
    }

    public MembreResponse toResponse(Membre m) {
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
                .collectiviteId(m.getCollectivite().getId())
                .collectiviteNom(m.getCollectivite().getNom())
                .build();
    }

    private PaiementResponse toPaiementResponse(Paiement p) {
        return PaiementResponse.builder()
                .id(p.getId())
                .montant(p.getMontant())
                .dateEncaissement(p.getDateEncaissement())
                .modePaiement(p.getModePaiement())
                .membreId(p.getMembre().getId())
                .membreNomPrenom(p.getMembre().getNom() + " " + p.getMembre().getPrenom())
                .cotisationId(p.getCotisation().getId())
                .cotisationDescription(p.getCotisation().getDescription())
                .build();
    }
}
