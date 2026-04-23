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
import mg.prog3.federation.repository.PaiementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompteService {

    private final CompteRepository compteRepository;
    private final CollectiviteRepository collectiviteRepository;
    private final PaiementRepository paiementRepository;

    @Transactional
    public CompteResponse creerCaisseCollectivite(Long collectiviteId, String nomTitulaire) {
        Collectivite collectivite = findCollectivite(collectiviteId);

        if (compteRepository.existsByCollectiviteIdAndTypeCompte(collectiviteId, TypeCompte.CASH_REGISTER)) {
            throw new ConflictException("Collectivite id=" + collectiviteId + " already has a cash register.");
        }

        Caisse caisse = Caisse.builder()
                .nomTitulaire(nomTitulaire)
                .solde(0L)
                .dateSolde(LocalDate.now())
                .typeCompte(TypeCompte.CASH_REGISTER)
                .collectivite(collectivite)
                .appartientFederation(false)
                .build();

        return toResponse(compteRepository.save(caisse));
    }

    @Transactional
    public CompteResponse creerCaisseFederation(String nomTitulaire) {
        if (compteRepository.existsByAppartientFederationTrueAndTypeCompte(TypeCompte.CASH_REGISTER)) {
            throw new ConflictException("The federation already has a cash register.");
        }

        Caisse caisse = Caisse.builder()
                .nomTitulaire(nomTitulaire)
                .solde(0L)
                .dateSolde(LocalDate.now())
                .typeCompte(TypeCompte.CASH_REGISTER)
                .collectivite(null)
                .appartientFederation(true)
                .build();

        return toResponse(compteRepository.save(caisse));
    }

    @Transactional
    public CompteResponse creerCompteBancaireCollectivite(Long collectiviteId, CreateCompteBancaireRequest req) {
        Collectivite collectivite = findCollectivite(collectiviteId);
        validateBankName(req.getNomBanque());
        validateUniqueRib(req.getNumeroCompteBancaire());

        CompteBancaire compte = CompteBancaire.builder()
                .nomTitulaire(req.getNomTitulaire())
                .nomBanque(req.getNomBanque())
                .numeroCompteBancaire(req.getNumeroCompteBancaire())
                .solde(0L)
                .dateSolde(LocalDate.now())
                .typeCompte(TypeCompte.BANK)
                .collectivite(collectivite)
                .appartientFederation(false)
                .build();

        return toResponse(compteRepository.save(compte));
    }

    @Transactional
    public CompteResponse creerCompteBancaireFederation(CreateCompteBancaireRequest req) {
        validateBankName(req.getNomBanque());
        validateUniqueRib(req.getNumeroCompteBancaire());

        CompteBancaire compte = CompteBancaire.builder()
                .nomTitulaire(req.getNomTitulaire())
                .nomBanque(req.getNomBanque())
                .numeroCompteBancaire(req.getNumeroCompteBancaire())
                .solde(0L)
                .dateSolde(LocalDate.now())
                .typeCompte(TypeCompte.BANK)
                .collectivite(null)
                .appartientFederation(true)
                .build();

        return toResponse(compteRepository.save(compte));
    }

    @Transactional
    public CompteResponse creerCompteMobileMoneyCollectivite(Long collectiviteId, CreateCompteMobileMoneyRequest req) {
        Collectivite collectivite = findCollectivite(collectiviteId);
        validateMobileMoneyService(req.getServiceMobileMoney());
        validateUniquePhoneNumber(req.getNumeroTelephone());

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

    @Transactional
    public CompteResponse creerCompteMobileMoneyFederation(CreateCompteMobileMoneyRequest req) {
        validateMobileMoneyService(req.getServiceMobileMoney());
        validateUniquePhoneNumber(req.getNumeroTelephone());

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

    @Transactional(readOnly = true)
    public List<CompteAvecSoldeResponse> getFinancialAccounts(Long collectiviteId, LocalDate at) {
        findCollectivite(collectiviteId);
        return compteRepository.findByCollectiviteId(collectiviteId).stream()
                .filter(c -> !c.getDateSolde().isAfter(at))
                .map(this::toAvecSoldeResponse)
                .toList();
    }

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

    private void validateBankName(String bankName) {
        List<String> allowedBanks = List.of(
                "BRED", "MCB", "BMOI", "BOA", "BGFI",
                "AFG", "ACCES_BANQUE", "BAOBAB", "SIPEM");
        if (!allowedBanks.contains(bankName)) {
            throw new BusinessException(
                    "Unrecognized bank: " + bankName + ". Allowed: " + allowedBanks);
        }
    }

    private void validateMobileMoneyService(String service) {
        List<String> allowedServices = List.of("Orange Money", "Mvola", "Airtel Money");
        if (!allowedServices.contains(service)) {
            throw new BusinessException(
                    "Unrecognized mobile money service: " + service + ". Allowed: " + allowedServices);
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

    private Collectivite findCollectivite(Long id) {
        return collectiviteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collectivite not found: " + id));
    }

    private Compte findCompte(Long id) {
        return compteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
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

        if (c instanceof CompteBancaire cb) {
            builder.nomBanque(cb.getNomBanque())
                   .numeroCompteBancaire(cb.getNumeroCompteBancaire());
        } else if (c instanceof CompteMobileMoney cm) {
            builder.serviceMobileMoney(cm.getServiceMobileMoney())
                   .numeroTelephone(cm.getNumeroTelephone());
        }
        return builder.build();
    }

    private CompteAvecSoldeResponse toAvecSoldeResponse(Compte c) {
        CompteAvecSoldeResponse.CompteAvecSoldeResponseBuilder b = CompteAvecSoldeResponse.builder()
                .id(c.getId())
                .typeCompte(c.getTypeCompte())
                .nomTitulaire(c.getNomTitulaire())
                .solde(c.getSolde())
                .dateSolde(c.getDateSolde());

        if (c instanceof CompteBancaire cb) {
            b.nomBanque(cb.getNomBanque())
             .numeroCompteBancaire(cb.getNumeroCompteBancaire());
        } else if (c instanceof CompteMobileMoney cm) {
            b.serviceMobileMoney(cm.getServiceMobileMoney())
             .numeroTelephone(cm.getNumeroTelephone());
        }
        return b.build();
    }
}
