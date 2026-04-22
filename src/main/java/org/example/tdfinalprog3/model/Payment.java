package org.example.tdfinalprog3.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tdfinalprog3.model.enums.PaymentMode;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Payment {
    private String id;
    private double amount;
    private PaymentMode paymentMode;
    private String memberId;
    private String membershipFeeId;
    private String accountId;
    private LocalDate paymentDate;

    public static Payment fromCreateRequest(CreatePaymentRequest request, String memberId) {
        Payment payment = new Payment();
        payment.setId(UUID.randomUUID().toString());
        payment.setAmount(request.getAmount());
        payment.setPaymentMode(PaymentMode.valueOf(request.getPaymentMode()));
        payment.setMemberId(memberId);
        payment.setMembershipFeeId(request.getMembershipFeeId());
        payment.setAccountId(request.getAccountId());
        payment.setPaymentDate(LocalDate.now());
        return payment;
    }
}