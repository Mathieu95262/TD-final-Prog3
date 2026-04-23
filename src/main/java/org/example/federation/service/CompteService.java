package org.example.federation.service;

import lombok.RequiredArgsConstructor;
import org.example.federation.dto.request.CreateCompteBancaireRequest;
import org.example.federation.dto.request.CreateCompteMobileMoneyRequest;
import org.example.federation.dto.response.CompteResponse;
import org.example.federation.entity.Caisse;
import org.example.federation.entity.Collectivite;
import org.example.federation.entity.Compte;
import org.example.federation.entity.CompteBancaire;
import org.example.federation.entity.CompteMobileMoney;
import org.example.federation.enums.TypeCompte;
import org.example.federation.exception.BusinessException;
import org.example.federation.exception.ConflictException;
import org.example.federation.exception.ResourceNotFoundException;
import org.example.federation.repository.CollectiviteRepository;
import org.example.federation.repository.CompteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompteService {

    private final CompteRepository compteRepository;
    private final CollectiviteRepository collectiviteRepository;

    // ─────────────────────────────────────────────────────────────────────────
    // D - Créer la caisse d'une collectivité
    // RÈGLE : une seule caisse par collectivité (ou par fédération)
    // ─────────────────────────────────────────────────────────────────────────

    @Transactional
    public CompteResponse creerCaisseCollectivite(Long collectiviteId, String nomTitulaire) {
        Collectivite collectivite = findCollectivite(collectiviteId);

        // Règle D : une seule caisse par collectivité
        if (compteRepository.existsByCollectiviteIdAndTypeCompte(collectiviteId, TypeCompte.CAISSE)) {
            throw new ConflictException(
                    "La collectivité id=" + collectiviteId
                            + " possède déjà une caisse. Une seule caisse est autorisée.");
        }

        Caisse caisse = Caisse.builder()
                .nomTitulaire(nomTitulaire)
                .solde(0L)
                .dateSolde(LocalDate.now())
                .typeCompte(TypeCompte.CAISSE)
                .collectivite(collectivite)
                .appartientFederation(false)
                .build();

        return toResponse(compteRepository.save(caisse));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // D - Créer la caisse de la fédération
    // RÈGLE : une seule caisse pour la fédération
    // ─────────────────────────────────────────────────────────────────────────

    @Transactional
    public CompteResponse creerCaisseFederation(String nomTitulaire) {

        // Règle D : une seule caisse pour la fédération
        if (compteRepository.existsByAppartientFederationTrueAndTypeCompte(TypeCompte.CAISSE)) {
            throw new ConflictException(
                    "La fédération possède déjà une caisse. Une seule caisse est autorisée.");
        }

        Caisse caisse = Caisse.builder()
                .nomTitulaire(nomTitulaire)
                .solde(0L)
                .dateSolde(LocalDate.now())
                .typeCompte(TypeCompte.CAISSE)
                .collectivite(null)
                .appartientFederation(true)
                .build();

        return toResponse(compteRepository.save(caisse));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // D - Créer un compte bancaire pour une collectivité
    // RÈGLE : plusieurs comptes bancaires possibles
    // Format RIB : 23 chiffres (BBBBBGGGGGCCCCCCCCCCCКК)
    // Banques : BRED, MCB, BMOI, BOA, BGFI, AFG, ACCES_BANQUE, BAOBAB, SIPEM
    // ─────────────────────────────────────────────────────────────────────────

    @Transactional
    public CompteResponse creerCompteBancaireCollectivite(Long collectiviteId,
                                                          CreateCompteBancaireRequest req) {
        Collectivite collectivite = findCollectivite(collectiviteId);

        validerNomBanque(req.getNomBanque());
        validerUniciteRib(req.getNumeroCompteBancaire());

        CompteBancaire compte = CompteBancaire.builder()
                .nomTitulaire(req.getNomTitulaire())
                .nomBanque(req.getNomBanque())
                .numeroCompteBancaire(req.getNumeroCompteBancaire())
                .solde(0L)
                .dateSolde(LocalDate.now())
                .typeCompte(TypeCompte.BANCAIRE)
                .collectivite(collectivite)
                .appartientFederation(false)
                .build();

        return toResponse(compteRepository.save(compte));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // D - Créer un compte bancaire pour la fédération
    // ─────────────────────────────────────────────────────────────────────────

    @Transactional
    public CompteResponse creerCompteBancaireFederation(CreateCompteBancaireRequest req) {
        validerNomBanque(req.getNomBanque());
        validerUniciteRib(req.getNumeroCompteBancaire());

        CompteBancaire compte = CompteBancaire.builder()
                .nomTitulaire(req.getNomTitulaire())
                .nomBanque(req.getNomBanque())
                .numeroCompteBancaire(req.getNumeroCompteBancaire())
                .solde(0L)
                .dateSolde(LocalDate.now())
                .typeCompte(TypeCompte.BANCAIRE)
                .collectivite(null)
                .appartientFederation(true)
                .build();

        return toResponse(compteRepository.save(compte));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // D - Créer un compte mobile money pour une collectivité
    // RÈGLE : plusieurs comptes mobile money possibles
    // Services : Orange Money, Mvola, Airtel Money
    // ─────────────────────────────────────────────────────────────────────────

    @Transactional
    public CompteResponse creerCompteMobileMoneyCollectivite(Long collectiviteId,
                                                             CreateCompteMobileMoneyRequest req) {
        Collectivite collectivite = findCollectivite(collectiviteId);

        validerServiceMobileMoney(req.getServiceMobileMoney());
        validerUniciteNumeroTelephone(req.getNumeroTelephone());

        CompteMobileMoney compte = CompteMobileMoney.builder()
                .nomTitulaire(req.getNomTitulaire())
                .serviceMobileMoney(req.getServiceMobileMoney())
                .numeroTelephone(req.getNumeroTelephone())
                .solde(0L)
                .dateSolde(LocalDate.now())
                .typeCompte(TypeCompte.MOBILE_MONEY)
                .collectivite(collectivite)
                .appartientFederation(false)
                .build();

        return toResponse(compteRepository.save(compte));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // D - Créer un compte mobile money pour la fédération
    // ─────────────────────────────────────────────────────────────────────────

    @Transactional
    public CompteResponse creerCompteMobileMoneyFederation(CreateCompteMobileMoneyRequest req) {
        validerServiceMobileMoney(req.getServiceMobileMoney());
        validerUniciteNumeroTelephone(req.getNumeroTelephone());

        CompteMobileMoney compte = CompteMobileMoney.builder()
                .nomTitulaire(req.getNomTitulaire())
                .serviceMobileMoney(req.getServiceMobileMoney())
                .numeroTelephone(req.getNumeroTelephone())
                .solde(0L)
                .dateSolde(LocalDate.now())
                .typeCompte(TypeCompte.MOBILE_MONEY)
                .collectivite(null)
                .appartientFederation(true)
                .build();

        return toResponse(compteRepository.save(compte));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // D - Lectures
    // ─────────────────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<CompteResponse> getComptesByCollectivite(Long collectiviteId) {
        findCollectivite(collectiviteId);
        return compteRepository.findByCollectiviteId(collectiviteId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<CompteResponse> getComptesFederation() {
        return compteRepository.findByAppartientFederationTrue()
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public CompteResponse getById(Long id) {
        return toResponse(findCompte(id));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Validations métier
    // ─────────────────────────────────────────────────────────────────────────

    private void validerNomBanque(String nomBanque) {
        List<String> banquesAutorisees = List.of(
                "BRED", "MCB", "BMOI", "BOA", "BGFI",
                "AFG", "ACCES_BANQUE", "BAOBAB", "SIPEM");
        if (!banquesAutorisees.contains(nomBanque)) {
            throw new BusinessException(
                    "Banque non reconnue : " + nomBanque
                            + ". Banques autorisées : " + banquesAutorisees);
        }
    }

    private void validerServiceMobileMoney(String service) {
        List<String> servicesAutorises = List.of("Orange Money", "Mvola", "Airtel Money");
        if (!servicesAutorises.contains(service)) {
            throw new BusinessException(
                    "Service mobile money non reconnu : " + service
                            + ". Services autorisés : " + servicesAutorises);
        }
    }

    private void validerUniciteRib(String rib) {
        if (compteRepository.existsByNumeroCompteBancaire(rib)) {
            throw new ConflictException(
                    "Un compte bancaire avec le RIB " + rib + " existe déjà.");
        }
    }

    private void validerUniciteNumeroTelephone(String telephone) {
        if (compteRepository.existsByNumeroTelephone(telephone)) {
            throw new ConflictException(
                    "Un compte mobile money avec le numéro " + telephone + " existe déjà.");
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────────────────────────────────

    private Collectivite findCollectivite(Long id) {
        return collectiviteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Collectivité introuvable : " + id));
    }

    private Compte findCompte(Long id) {
        return compteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Compte introuvable : " + id));
    }

    public CompteResponse toResponse(Compte c) {
        CompteResponse.CompteResponseBuilder builder = CompteResponse.builder()
                .id(c.getId())
                .typeCompte(c.getTypeCompte())
                .nomTitulaire(c.getNomTitulaire())
                .solde(c.getSolde())
                .dateSolde(c.getDateSolde())
                .appartientFederation(c.isAppartientFederation())
                .collectiviteId(c.getCollectivite() != null ? c.getCollectivite().getId() : null);

        // Champs spécifiques selon le type
        if (c instanceof CompteBancaire cb) {
            builder.nomBanque(cb.getNomBanque())
                    .numeroCompteBancaire(cb.getNumeroCompteBancaire());
        } else if (c instanceof CompteMobileMoney cm) {
            builder.serviceMobileMoney(cm.getServiceMobileMoney())
                    .numeroTelephone(cm.getNumeroTelephone());
        }

        return builder.build();
    }
}