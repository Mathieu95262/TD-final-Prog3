package org.example.tdfinalprog3.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tdfinalprog3.model.enums.AccountType;
import org.example.tdfinalprog3.model.enums.Bank;
import org.example.tdfinalprog3.model.enums.MobileBankingService;
import java.util.UUID;

@Data
@NoArgsConstructor
public class FinancialAccount {
    private String id;
    private AccountType accountType;
    private String holderName;
    private Bank bankName;
    private String bankCode;
    private String branchCode;
    private String accountNumber;
    private String accountKey;
    private MobileBankingService mobileService;
    private String phoneNumber;
    private double balance;
    private String collectivityId;

    public FinancialAccount(String collectivityId) {
        this.id = UUID.randomUUID().toString();
        this.collectivityId = collectivityId;
        this.balance = 0.0;
    }

    public void credit(double amount) {
        this.balance += amount;
    }

    public void debit(double amount) {
        if (this.balance < amount) {
            throw new RuntimeException("Insufficient funds in account " + this.id);
        }
        this.balance -= amount;
    }
}