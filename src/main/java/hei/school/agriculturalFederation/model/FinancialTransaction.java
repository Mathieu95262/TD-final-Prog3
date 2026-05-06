package hei.school.agriculturalFederation.model;

import java.time.LocalDate;

public class FinancialTransaction {
    private String id;
    private String financialAccountId;
    private String collectivityId;
    private long amount;
    private TransactionType transactionType;
    private PaymentMode paymentMode;
    private LocalDate transactionDate;
    private String description;

    public FinancialTransaction() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getFinancialAccountId() { return financialAccountId; }
    public void setFinancialAccountId(String financialAccountId) { this.financialAccountId = financialAccountId; }

    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }

    public long getAmount() { return amount; }
    public void setAmount(long amount) { this.amount = amount; }

    public TransactionType getTransactionType() { return transactionType; }
    public void setTransactionType(TransactionType transactionType) { this.transactionType = transactionType; }

    public PaymentMode getPaymentMode() { return paymentMode; }
    public void setPaymentMode(PaymentMode paymentMode) { this.paymentMode = paymentMode; }

    public LocalDate getTransactionDate() { return transactionDate; }
    public void setTransactionDate(LocalDate transactionDate) { this.transactionDate = transactionDate; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
