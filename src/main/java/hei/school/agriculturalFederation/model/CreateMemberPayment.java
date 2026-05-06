package hei.school.agriculturalFederation.model;

import java.time.LocalDate;

public class CreateMemberPayment {
    private String membershipFeeId;
    private long amount;
    private LocalDate paymentDate;
    private PaymentMode paymentMode;

    public CreateMemberPayment() {}

    public String getMembershipFeeId() { return membershipFeeId; }
    public void setMembershipFeeId(String membershipFeeId) { this.membershipFeeId = membershipFeeId; }

    public long getAmount() { return amount; }
    public void setAmount(long amount) { this.amount = amount; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

    public PaymentMode getPaymentMode() { return paymentMode; }
    public void setPaymentMode(PaymentMode paymentMode) { this.paymentMode = paymentMode; }
}
