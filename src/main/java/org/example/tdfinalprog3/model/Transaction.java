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
public class Transaction {
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate creationDate;

    private double amount;
    private PaymentMode paymentMode;
    private String collectivityId;
    private String accountId;
    private String memberId;

    public static Transaction fromPayment(Payment payment, String collectivityId) {
        Transaction transaction = new Transaction();
        transaction.id = UUID.randomUUID().toString();
        transaction.creationDate = payment.getPaymentDate();
        transaction.amount = payment.getAmount();
        transaction.paymentMode = payment.getPaymentMode();
        transaction.collectivityId = collectivityId;
        transaction.accountId = payment.getAccountId();
        transaction.memberId = payment.getMemberId();
        return transaction;
    }
}