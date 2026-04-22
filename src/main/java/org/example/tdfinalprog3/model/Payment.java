package org.example.tdfinalprog3.model;

import org.example.tdfinalprog3.model.enums.PaymentMode;
import java.time.LocalDate;
import java.util.UUID;

public class Payment {
    private String id;
    private double amount;
    private PaymentMode paymentMode;
    private String memberId;
    private String membershipFeeId;
    private String accountId;
    private LocalDate paymentDate;

    public Payment() {
        this.id = UUID.randomUUID().toString();
        this.paymentDate = LocalDate.now();
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public PaymentMode getPaymentMode() { return paymentMode; }
    public void setPaymentMode(PaymentMode paymentMode) { this.paymentMode = paymentMode; }
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    public String getMembershipFeeId() { return membershipFeeId; }
    public void setMembershipFeeId(String membershipFeeId) { this.membershipFeeId = membershipFeeId; }
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
}
