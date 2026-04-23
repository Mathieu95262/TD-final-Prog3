package org.example.federation.service;

import lombok.RequiredArgsConstructor;
import org.example.federation.dto.request.CreateCotisationRequest;
import org.example.federation.dto.request.CreatePaiementRequest;
import org.example.federation.dto.response.CotisationResponse;
import org.example.federation.dto.response.PaiementResponse;
import org.example.federation.entity.Collectivite;
import org.example.federation.entity.Cotisation;
import org.example.federation.entity.Membre;
import org.example.federation.entity.Paiement;
import org.example.federation.exception.BusinessException;
import org.example.federation.exception.ResourceNotFoundException;
import org.example.federation.repository.CollectiviteRepository;
import org.example.federation.repository.CotisationRepository;
import org.example.federation.repository.MembreRepository;
import org.example.federation.repository.PaiementRepository;
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

    // ─────────────────────────────────────────────────────────────────────────
    // C - Enregistrer un paiement (encaissement par le trésorier)
    // Trace : montant + date encaissement + mode paiement
    // ─────────────────────────────────────────────────────────────────────────

    @Transactional
    public PaiementResponse enregistrerPaiement(Long collectiviteId, CreatePaiementRequest req) {

        // Vérifier que la collectivité existe
        findCollectivite(collectiviteId);

        // Vérifier que le membre existe et appartient à cette collectivité
        Membre membre = membreRepository.findById(req.getMembreId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Membre introuvable : " + req.getMembreId()));

        if (!membre.getCollectivite().getId().equals(collectiviteId)) {
            throw new BusinessException(
                    "Le membre id=" + req.getMembreId()
                            + " n'appartient pas à la collectivité id=" + collectiviteId);
        }

        Cotisation cotisation = cotisationRepository.findById(req.getCotisationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cotisation introuvable : " + req.getCotisationId()));

        if (!cotisation.getCollectivite().getId().equals(collectiviteId)) {
            throw new BusinessException(
                    "La cotisation id=" + req.getCotisationId()
                            + " n'appartient pas à la collectivité id=" + collectiviteId);
        }

        // Construire et sauvegarder le paiement
        Paiement paiement = Paiement.builder()
                .montant(req.getMontant())
                .dateEncaissement(req.getDateEncaissement())
                .modePaiement(req.getModePaiement())
                .membre(membre)
                .cotisation(cotisation)
                .build();

        return toPaiementResponse(paiementRepository.save(paiement));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // C - Lectures
    // ─────────────────────────────────────────────────────────────────────────

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
            throw new ResourceNotFoundException("Membre introuvable : " + membreId);
        }
        return paiementRepository.findByMembreId(membreId)
                .stream().map(this::toPaiementResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<PaiementResponse> getPaiementsByCollectiviteAndPeriode(
            Long collectiviteId, LocalDate debut, LocalDate fin) {

        findCollectivite(collectiviteId);

        if (debut.isAfter(fin)) {
            throw new BusinessException(
                    "La date de début doit être antérieure à la date de fin.");
        }

        return paiementRepository.findByCollectiviteAndPeriode(collectiviteId, debut, fin)
                .stream().map(this::toPaiementResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<PaiementResponse> getPaiementsByCotisation(Long cotisationId) {
        if (!cotisationRepository.existsById(cotisationId)) {
            throw new ResourceNotFoundException("Cotisation introuvable : " + cotisationId);
        }
        return paiementRepository.findByCotisationId(cotisationId)
                .stream().map(this::toPaiementResponse).toList();
    }


    private Collectivite findCollectivite(Long id) {
        return collectiviteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Collectivité introuvable : " + id));
    }

    private Cotisation findCotisation(Long id) {
        return cotisationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Cotisation introuvable : " + id));
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
