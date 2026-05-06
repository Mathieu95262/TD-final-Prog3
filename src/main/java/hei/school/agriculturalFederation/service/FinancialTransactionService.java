package hei.school.agriculturalFederation.service;

import hei.school.agriculturalFederation.exception.NotFoundException;
import hei.school.agriculturalFederation.model.FinancialTransaction;
import hei.school.agriculturalFederation.repository.CollectivityRepository;
import hei.school.agriculturalFederation.repository.FinancialTransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FinancialTransactionService {

    private final FinancialTransactionRepository transactionRepository;
    private final CollectivityRepository collectivityRepository;

    public FinancialTransactionService(FinancialTransactionRepository transactionRepository,
                                       CollectivityRepository collectivityRepository) {
        this.transactionRepository = transactionRepository;
        this.collectivityRepository = collectivityRepository;
    }

    /** GET /collectivities/{id}/transactions?from=DATE&to=DATE */
    public List<FinancialTransaction> getTransactions(String collectivityId,
                                                       LocalDate from, LocalDate to) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new NotFoundException("Collectivity not found: " + collectivityId);
        }
        return transactionRepository.findByCollectivityAndPeriod(collectivityId, from, to);
    }
}
