package mg.prog3.federation.service;

import lombok.RequiredArgsConstructor;
import mg.prog3.federation.dto.request.CreateCotisationRequest;
import mg.prog3.federation.dto.request.CreatePaiementRequest;
import mg.prog3.federation.dto.response.CotisationResponse;
import mg.prog3.federation.dto.response.PaiementResponse;
import mg.prog3.federation.entity.Cotisation;
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
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CotisationService {

    private final CotisationRepository cotisationRepository;
    private final PaiementRepository paiementRepository;
    private final CollectiviteRepository collectiviteRepository;
    private final MembreRepository membreRepository;

    @Transactional
    public CotisationResponse creerCotisation(Long collectiviteId, CreateCotisationRequest req) {
        if (!collectiviteRepository.existsById(collectiviteId)) {
            throw new ResourceNotFoundException("Collectivite not found: " + collectiviteId);
        }

        Cotisation cotisation = Cotisation.builder()
                .typeCotisation(req.getTypeCotisation())
                .montant(req.getMontant())
                .description(req.getDescription())
                .collectiviteId(collectiviteId)
                .build();

        return toResponse(cotisationRepository.save(cotisation));
    }

    @Transactional
    public PaiementResponse enregistrerPaiement(Long collectiviteId, CreatePaiementRequest req) {
        if (!collectiviteRepository.existsById(collectiviteId)) {
            throw new ResourceNotFoundException("Collectivite not found: " + collectiviteId);
        }

        if (!membreRepository.existsById(req.getMembreId())) {
            throw new ResourceNotFoundException("Member not found: " + req.getMembreId());
        }

        Cotisation cotisation = cotisationRepository.findById(req.getCotisationId())
                .orElseThrow(() -> new ResourceNotFoundException("Membership fee not found: " + req.getCotisationId()));

        if (!cotisation.getCollectiviteId().equals(collectiviteId)) {
            throw new BusinessException(
                    "Membership fee id=" + req.getCotisationId()
                            + " does not belong to collectivite id=" + collectiviteId);
        }

        Paiement paiement = Paiement.builder()
                .montant(req.getMontant())
                .dateEncaissement(req.getDateEncaissement())
                .modePaiement(req.getModePaiement())
                .membreId(req.getMembreId())
                .cotisationId(req.getCotisationId())
                .build();

        return toPaiementResponse(paiementRepository.save(paiement));
    }

    @Transactional(readOnly = true)
    public Collection<CotisationResponse> getCotisationsByCollectivite(Long collectiviteId) {
        if (!collectiviteRepository.existsById(collectiviteId)) {
            throw new ResourceNotFoundException("Collectivite not found: " + collectiviteId);
        }
        return cotisationRepository.findByCollectiviteId(collectiviteId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public CotisationResponse getCotisationById(Long id) {
        return toResponse(findCotisation(id));
    }

    @Transactional
    public CotisationResponse toggleCotisationStatus(Long collectiviteId, Long cotisationId) {
        Cotisation cotisation = cotisationRepository.findById(cotisationId)
                .orElseThrow(() -> new ResourceNotFoundException("Cotisation not found: " + cotisationId));

        if (!cotisation.getCollectiviteId().equals(collectiviteId)) {
            throw new BusinessException("Cotisation does not belong to collectivite: " + collectiviteId);
        }

        return toResponse(cotisationRepository.save(cotisation));
    }

    @Transactional(readOnly = true)
    public Collection<PaiementResponse> getPaiementsByMembre(Long membreId) {
        if (!membreRepository.existsById(membreId)) {
            throw new ResourceNotFoundException("Member not found: " + membreId);
        }
        return paiementRepository.findByMembreId(membreId)
                .stream().map(this::toPaiementResponse).toList();
    }

    @Transactional(readOnly = true)
    public Collection<PaiementResponse> getPaiementsByCollectiviteAndPeriode(
            Long collectiviteId, LocalDate debut, LocalDate fin) {

        if (!collectiviteRepository.existsById(collectiviteId)) {
            throw new ResourceNotFoundException("Collectivite not found: " + collectiviteId);
        }

        if (debut.isAfter(fin)) {
            throw new BusinessException("Start date must be before end date.");
        }

        return paiementRepository.findByCollectiviteIdAndPeriode(collectiviteId, debut, fin)
                .stream().map(this::toPaiementResponse).toList();
    }

    @Transactional(readOnly = true)
    public Collection<PaiementResponse> getPaiementsByCotisation(Long cotisationId) {
        if (!cotisationRepository.existsById(cotisationId)) {
            throw new ResourceNotFoundException("Membership fee not found: " + cotisationId);
        }
        return paiementRepository.findByCotisationId(cotisationId)
                .stream().map(this::toPaiementResponse).toList();
    }

    private Cotisation findCotisation(Long id) {
        return cotisationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membership fee not found: " + id));
    }

    private CotisationResponse toResponse(Cotisation c) {
        String collectiviteNom = collectiviteRepository.findById(c.getCollectiviteId())
                .map(coll -> coll.getNom())
                .orElse(null);

        return CotisationResponse.builder()
                .id(c.getId())
                .typeCotisation(c.getTypeCotisation())
                .montant(c.getMontant())
                .description(c.getDescription())
                .collectiviteId(c.getCollectiviteId())
                .collectiviteNom(collectiviteNom)
                .build();
    }

    private PaiementResponse toPaiementResponse(Paiement p) {
        String membreName = membreRepository.findById(p.getMembreId())
                .map(m -> m.getNom() + " " + m.getPrenom())
                .orElse("Inconnu");

        String cotisationDesc = cotisationRepository.findById(p.getCotisationId())
                .map(Cotisation::getDescription)
                .orElse(null);

        return PaiementResponse.builder()
                .id(p.getId())
                .montant(p.getMontant())
                .dateEncaissement(p.getDateEncaissement())
                .modePaiement(p.getModePaiement())
                .membreId(p.getMembreId())
                .membreNomPrenom(membreName)
                .cotisationId(p.getCotisationId())
                .cotisationDescription(cotisationDesc)
                .build();
    }
}