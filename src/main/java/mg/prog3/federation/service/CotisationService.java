package mg.prog3.federation.service;

import lombok.RequiredArgsConstructor;
import mg.prog3.federation.dto.request.CreateCotisationRequest;
import mg.prog3.federation.dto.request.CreatePaiementRequest;
import mg.prog3.federation.dto.response.CotisationResponse;
import mg.prog3.federation.dto.response.PaiementResponse;
import mg.prog3.federation.entity.Collectivite;
import mg.prog3.federation.entity.Cotisation;
import mg.prog3.federation.entity.Membre;
import mg.prog3.federation.entity.Paiement;
import mg.prog3.federation.exception.BusinessException;
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
public class CotisationService {

    private final CotisationRepository cotisationRepository;
    private final PaiementRepository paiementRepository;
    private final CollectiviteRepository collectiviteRepository;
    private final MembreRepository membreRepository;

    @Transactional
    public CotisationResponse creerCotisation(Long collectiviteId, CreateCotisationRequest req) {
        Collectivite collectivite = findCollectivite(collectiviteId);

        Cotisation cotisation = Cotisation.builder()
                .typeCotisation(req.getTypeCotisation())
                .montant(req.getMontant())
                .description(req.getDescription())
                .collectivite(collectivite)
                .build();

        return toResponse(cotisationRepository.save(cotisation));
    }

    @Transactional
    public PaiementResponse enregistrerPaiement(Long collectiviteId, CreatePaiementRequest req) {
        findCollectivite(collectiviteId);

        Membre membre = membreRepository.findById(req.getMembreId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found: " + req.getMembreId()));

        if (!membre.getCollectivite().getId().equals(collectiviteId)) {
            throw new BusinessException(
                    "Member id=" + req.getMembreId()
                    + " does not belong to collectivite id=" + collectiviteId);
        }

        Cotisation cotisation = cotisationRepository.findById(req.getCotisationId())
                .orElseThrow(() -> new ResourceNotFoundException("Membership fee not found: " + req.getCotisationId()));

        if (!cotisation.getCollectivite().getId().equals(collectiviteId)) {
            throw new BusinessException(
                    "Membership fee id=" + req.getCotisationId()
                    + " does not belong to collectivite id=" + collectiviteId);
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
    public List<CotisationResponse> getCotisationsByCollectivite(Long collectiviteId) {
        findCollectivite(collectiviteId);
        return cotisationRepository.findByCollectiviteId(collectiviteId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public CotisationResponse getCotisationById(Long id) {
        return toResponse(findCotisation(id));
    }

    @Transactional(readOnly = true)
    public List<PaiementResponse> getPaiementsByMembre(Long membreId) {
        if (!membreRepository.existsById(membreId)) {
            throw new ResourceNotFoundException("Member not found: " + membreId);
        }
        return paiementRepository.findByMembreId(membreId)
                .stream().map(this::toPaiementResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<PaiementResponse> getPaiementsByCollectiviteAndPeriode(
            Long collectiviteId, LocalDate debut, LocalDate fin) {

        findCollectivite(collectiviteId);

        if (debut.isAfter(fin)) {
            throw new BusinessException("Start date must be before end date.");
        }

        return paiementRepository.findByCollectiviteIdAndPeriode(collectiviteId, debut, fin)
                .stream().map(this::toPaiementResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<PaiementResponse> getPaiementsByCotisation(Long cotisationId) {
        if (!cotisationRepository.existsById(cotisationId)) {
            throw new ResourceNotFoundException("Membership fee not found: " + cotisationId);
        }
        return paiementRepository.findByCotisationId(cotisationId)
                .stream().map(this::toPaiementResponse).toList();
    }

    private Collectivite findCollectivite(Long id) {
        return collectiviteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collectivite not found: " + id));
    }

    private Cotisation findCotisation(Long id) {
        return cotisationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membership fee not found: " + id));
    }

    private CotisationResponse toResponse(Cotisation c) {
        return CotisationResponse.builder()
                .id(c.getId())
                .typeCotisation(c.getTypeCotisation())
                .montant(c.getMontant())
                .description(c.getDescription())
                .collectiviteId(c.getCollectivite().getId())
                .collectiviteNom(c.getCollectivite().getNom())
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
