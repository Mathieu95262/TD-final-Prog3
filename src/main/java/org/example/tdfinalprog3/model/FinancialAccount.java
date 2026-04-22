package org.example.tdfinalprog3.model;

import org.example.tdfinalprog3.model.enums.AccountType;
import org.example.tdfinalprog3.model.enums.Bank;
import org.example.tdfinalprog3.model.enums.MobileBankingService;
import java.util.UUID;

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

    public FinancialAccount() {
        this.id = UUID.randomUUID().toString();
        this.balance = 0.0;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }
    public String getHolderName() { return holderName; }
    public void setHolderName(String holderName) { this.holderName = holderName; }
    public Bank getBankName() { return bankName; }
    public void setBankName(Bank bankName) { this.bankName = bankName; }
    public String getBankCode() { return bankCode; }
    public void setBankCode(String bankCode) { this.bankCode = bankCode; }
    public String getBranchCode() { return branchCode; }
    public void setBranchCode(String branchCode) { this.branchCode = branchCode; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getAccountKey() { return accountKey; }
    public void setAccountKey(String accountKey) { this.accountKey = accountKey; }
    public MobileBankingService getMobileService() { return mobileService; }
    public void setMobileService(MobileBankingService mobileService) { this.mobileService = mobileService; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }

    public void addMoney(double amount) {
        this.balance = this.balance + amount;
    }

    public boolean hasEnoughMoney(double amount) {
        return this.balance >= amount;
    }
}