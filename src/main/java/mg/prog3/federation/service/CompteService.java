package mg.prog3.federation.service;

import lombok.RequiredArgsConstructor;
import mg.prog3.federation.dto.request.CreateCompteBancaireRequest;
import mg.prog3.federation.dto.request.CreateCompteMobileMoneyRequest;
import mg.prog3.federation.dto.response.CompteAvecSoldeResponse;
import mg.prog3.federation.dto.response.CompteResponse;
import mg.prog3.federation.entity.*;
import mg.prog3.federation.enums.TypeCompte;
import mg.prog3.federation.exception.BusinessException;
import mg.prog3.federation.exception.ConflictException;
import mg.prog3.federation.exception.ResourceNotFoundException;
import mg.prog3.federation.repository.CollectiviteRepository;
import mg.prog3.federation.repository.CompteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompteService {

    private final CompteRepository compteRepository;
    private final CollectiviteRepository collectiviteRepository;

    private static final Set<String> ALLOWED_BANKS = Set.of(
            "BRED", "MCB", "BMOI", "BOA", "BGFI",
            "AFG", "ACCES_BANQUE", "BAOBAB", "SIPEM");

    private static final Set<String> ALLOWED_MOBILE_SERVICES = Set.of(
            "ORANGE_MONEY", "MVOLA", "AIRTEL_MONEY");

    @Transactional
    public CompteResponse creerCaisseCollectivite(Long collectiviteId, String nomTitulaire) {
        if (!collectiviteRepository.existsById(collectiviteId)) {
            throw new ResourceNotFoundException("Collectivite not found: " + collectiviteId);
        }

        if (compteRepository.existsByCollectiviteIdAndTypeCompte(collectiviteId, TypeCompte.CASH_REGISTER)) {
            throw new ConflictException("Collectivite id=" + collectiviteId + " already has a cash register.");
        }

        Compte caisse = Compte.builder()
                .nomTitulaire(nomTitulaire)
                .solde(0L)
                .dateSolde(LocalDate.now())
                .typeCompte(TypeCompte.CASH_REGISTER)
                .collectiviteId(collectiviteId)
                .appartientFederation(false)
                .build();

        return toResponse(compteRepository.save(caisse));
    }

    @Transactional
    public CompteResponse creerCaisseFederation(String nomTitulaire) {
        if (compteRepository.existsByAppartientFederationTrueAndTypeCompte(TypeCompte.CASH_REGISTER)) {
            throw new ConflictException("The federation already has a cash register.");
        }

        Compte caisse = Compte.builder()
                .nomTitulaire(nomTitulaire)
                .solde(0L)
                .dateSolde(LocalDate.now())
                .typeCompte(TypeCompte.CASH_REGISTER)
                .appartientFederation(true)
                .build();

        return toResponse(compteRepository.save(caisse));
    }

    @Transactional
    public CompteResponse creerCompteBancaireCollectivite(Long collectiviteId, CreateCompteBancaireRequest req) {
        if (!collectiviteRepository.existsById(collectiviteId)) {
            throw new ResourceNotFoundException("Collectivite not found: " + collectiviteId);
        }
        validateBankName(req.getNomBanque());
        validateUniqueRib(req.getNumeroCompteBancaire());

        Compte compte = Compte.builder()
                .nomTitulaire(req.getNomTitulaire())
                .nomBanque(req.getNomBanque())
                .numeroCompteBancaire(req.getNumeroCompteBancaire())
                .solde(0L)
                .dateSolde(LocalDate.now())
                .typeCompte(TypeCompte.BANK)
                .collectiviteId(collectiviteId)
                .appartientFederation(false)
                .build();

        return toResponse(compteRepository.save(compte));
    }

    @Transactional
    public CompteResponse creerCompteBancaireFederation(CreateCompteBancaireRequest req) {
        validateBankName(req.getNomBanque());
        validateUniqueRib(req.getNumeroCompteBancaire());

        Compte compte = Compte.builder()
                .nomTitulaire(req.getNomTitulaire())
                .nomBanque(req.getNomBanque())
                .numeroCompteBancaire(req.getNumeroCompteBancaire())
                .solde(0L)
                .dateSolde(LocalDate.now())
                .typeCompte(TypeCompte.BANK)
                .appartientFederation(true)
                .build();

        return toResponse(compteRepository.save(compte));
    }

    @Transactional
    public CompteResponse creerCompteMobileMoneyCollectivite(Long collectiviteId, CreateCompteMobileMoneyRequest req) {
        if (!collectiviteRepository.existsById(collectiviteId)) {
            throw new ResourceNotFoundException("Collectivite not found: " + collectiviteId);
        }
        validateMobileMoneyService(req.getServiceMobileMoney());
        validateUniquePhoneNumber(req.getNumeroTelephone());

        Compte compte = Compte.builder()
                .nomTitulaire(req.getNomTitulaire())
                .serviceMobileMoney(req.getServiceMobileMoney())
                .numeroTelephone(req.getNumeroTelephone())
                .solde(0L)
                .dateSolde(LocalDate.now())
                .typeCompte(TypeCompte.MOBILE_MONEY)
                .collectiviteId(collectiviteId)
                .appartientFederation(false)
                .build();

        return toResponse(compteRepository.save(compte));
    }

    @Transactional
    public CompteResponse creerCompteMobileMoneyFederation(CreateCompteMobileMoneyRequest req) {
        validateMobileMoneyService(req.getServiceMobileMoney());
        validateUniquePhoneNumber(req.getNumeroTelephone());

        Compte compte = Compte.builder()
                .nomTitulaire(req.getNomTitulaire())
                .serviceMobileMoney(req.getServiceMobileMoney())
                .numeroTelephone(req.getNumeroTelephone())
                .solde(0L)
                .dateSolde(LocalDate.now())
                .typeCompte(TypeCompte.MOBILE_MONEY)
                .appartientFederation(true)
                .build();

        return toResponse(compteRepository.save(compte));
    }

    @Transactional(readOnly = true)
    public Collection<CompteAvecSoldeResponse> getFinancialAccounts(Long collectiviteId, LocalDate at) {
        if (!collectiviteRepository.existsById(collectiviteId)) {
            throw new ResourceNotFoundException("Collectivite not found: " + collectiviteId);
        }
        return compteRepository.findByCollectiviteId(collectiviteId).stream()
                .filter(c -> !c.getDateSolde().isAfter(at))
                .map(this::toAvecSoldeResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public Collection<CompteResponse> getComptesByCollectivite(Long collectiviteId) {
        if (!collectiviteRepository.existsById(collectiviteId)) {
            throw new ResourceNotFoundException("Collectivite not found: " + collectiviteId);
        }
        return compteRepository.findByCollectiviteId(collectiviteId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Collection<CompteResponse> getComptesFederation() {
        return compteRepository.findByAppartientFederationTrue()
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public CompteResponse getById(Long id) {
        return toResponse(findCompte(id));
    }

    private void validateBankName(String bankName) {
        if (!ALLOWED_BANKS.contains(bankName)) {
            throw new BusinessException(
                    "Unrecognized bank: " + bankName + ". Allowed: " + ALLOWED_BANKS);
        }
    }

    private void validateMobileMoneyService(String service) {
        if (!ALLOWED_MOBILE_SERVICES.contains(service)) {
            throw new BusinessException(
                    "Unrecognized mobile money service: " + service + ". Allowed: " + ALLOWED_MOBILE_SERVICES);
        }
    }

    private void validateUniqueRib(String rib) {
        if (compteRepository.existsByNumeroCompteBancaire(rib)) {
            throw new ConflictException("Account number already in use: " + rib);
        }
    }

    private void validateUniquePhoneNumber(String phoneNumber) {
        if (compteRepository.existsByNumeroTelephone(phoneNumber)) {
            throw new ConflictException("Phone number already in use: " + phoneNumber);
        }
    }

    private Compte findCompte(Long id) {
        return compteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
    }

    private CompteResponse toResponse(Compte c) {
        CompteResponse.CompteResponseBuilder builder = CompteResponse.builder()
                .id(c.getId())
                .typeCompte(c.getTypeCompte())
                .nomTitulaire(c.getNomTitulaire())
                .solde(c.getSolde())
                .dateSolde(c.getDateSolde())
                .appartientFederation(c.isAppartientFederation())
                .collectiviteId(c.getCollectiviteId())
                .nomBanque(c.getNomBanque())
                .numeroCompteBancaire(c.getNumeroCompteBancaire())
                .serviceMobileMoney(c.getServiceMobileMoney())
                .numeroTelephone(c.getNumeroTelephone());

        return builder.build();
    }

    private CompteAvecSoldeResponse toAvecSoldeResponse(Compte c) {
        return CompteAvecSoldeResponse.builder()
                .id(c.getId())
                .typeCompte(c.getTypeCompte())
                .nomTitulaire(c.getNomTitulaire())
                .solde(c.getSolde())
                .dateSolde(c.getDateSolde())
                .nomBanque(c.getNomBanque())
                .numeroCompteBancaire(c.getNumeroCompteBancaire())
                .serviceMobileMoney(c.getServiceMobileMoney())
                .numeroTelephone(c.getNumeroTelephone())
                .build();
    }
}