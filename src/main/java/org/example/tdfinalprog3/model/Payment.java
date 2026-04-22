package org.example.tdfinalprog3.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tdfinalprog3.model.enums.PaymentMode;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private String id;
    private double amount;
    private PaymentMode paymentMode;
    private String memberId;
    private String membershipFeeId;
    private String accountId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate paymentDate;

    public static Payment create(double amount, PaymentMode paymentMode,
                                 String memberId, String membershipFeeId, String accountId) {
        Payment payment = new Payment();
        payment.id = UUID.randomUUID().toString();
        payment.amount = amount;
        payment.paymentMode = paymentMode;
        payment.memberId = memberId;
        payment.membershipFeeId = membershipFeeId;
        payment.accountId = accountId;
        payment.paymentDate = LocalDate.now();
        return payment;
    }
}