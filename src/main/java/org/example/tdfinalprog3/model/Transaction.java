package org.example.tdfinalprog3.model;


import org.example.tdfinalprog3.model.enums.PaymentMode;
import java.time.LocalDate;
import java.util.UUID;

public class Transaction {
    private String id;
    private LocalDate creationDate;
    private double amount;
    private PaymentMode paymentMode;
    private String collectivityId;
    private String accountId;
    private String memberId;

    public Transaction() {
        this.id = UUID.randomUUID().toString();
        this.creationDate = LocalDate.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public PaymentMode getPaymentMode() { return paymentMode; }
    public void setPaymentMode(PaymentMode paymentMode) { this.paymentMode = paymentMode; }
    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
}