package hei.school.agriculturalFederation.service;

import hei.school.agriculturalFederation.exception.BadRequestException;
import hei.school.agriculturalFederation.exception.NotFoundException;
import hei.school.agriculturalFederation.model.AccountType;
import hei.school.agriculturalFederation.model.CreateFinancialAccount;
import hei.school.agriculturalFederation.model.FinancialAccount;
import hei.school.agriculturalFederation.repository.CollectivityRepository;
import hei.school.agriculturalFederation.repository.FinancialAccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FinancialAccountService {

    private final FinancialAccountRepository financialAccountRepository;
    private final CollectivityRepository collectivityRepository;

    public FinancialAccountService(FinancialAccountRepository financialAccountRepository,
                                   CollectivityRepository collectivityRepository) {
        this.financialAccountRepository = financialAccountRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<FinancialAccount> createAccounts(String collectivityId,
                                                  List<CreateFinancialAccount> requests) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new NotFoundException("Collectivity not found: " + collectivityId);
        }
        List<FinancialAccount> result = new ArrayList<>();
        for (CreateFinancialAccount req : requests) {
            if (req.getAccountType() == null) {
                throw new BadRequestException("Account type is required.");
            }
            // Only one CASH account allowed per collectivity
            if (req.getAccountType() == AccountType.CASH
                    && financialAccountRepository.hasCashAccount(collectivityId)) {
                throw new BadRequestException(
                        "A collectivity can only have one CASH account.");
            }
            FinancialAccount account = new FinancialAccount();
            account.setId(UUID.randomUUID().toString());
            account.setCollectivityId(collectivityId);
            account.setAccountType(req.getAccountType());
            account.setAccountHolder(req.getAccountHolder());
            account.setBankName(req.getBankName());
            account.setAccountNumber(req.getAccountNumber());
            account.setMobileHolder(req.getMobileHolder());
            account.setMobileService(req.getMobileService());
            account.setMobilePhone(req.getMobilePhone());
            account.setCurrentBalance(req.getInitialBalance());
            result.add(financialAccountRepository.save(account));
        }
        return result;
    }

    /** GET /collectivities/{id}/financialAccounts?at=DATE */
    public List<FinancialAccount> getAccountsWithBalanceAt(String collectivityId, LocalDate at) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new NotFoundException("Collectivity not found: " + collectivityId);
        }
        LocalDate effectiveDate = at != null ? at : LocalDate.now();
        List<FinancialAccount> accounts =
                financialAccountRepository.findAllByCollectivityId(collectivityId);
        // Recompute each account's balance at the given date
        for (FinancialAccount account : accounts) {
            long balance = financialAccountRepository.getBalanceAt(account.getId(), effectiveDate);
            account.setCurrentBalance(balance);
        }
        return accounts;
    }
}
