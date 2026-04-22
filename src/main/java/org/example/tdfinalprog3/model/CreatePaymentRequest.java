package org.example.tdfinalprog3.model;

public class CreatePaymentRequest {
    private double amount;
    private String membershipFeeId;
    private String accountId;
    private String paymentMode;

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getMembershipFeeId() { return membershipFeeId; }
    public void setMembershipFeeId(String membershipFeeId) { this.membershipFeeId = membershipFeeId; }
    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }
    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }
}