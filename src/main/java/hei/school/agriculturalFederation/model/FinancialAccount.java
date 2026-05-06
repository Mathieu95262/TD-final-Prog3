package hei.school.agriculturalFederation.model;

public class FinancialAccount {
    private String id;
    private String collectivityId;
    private AccountType accountType;
    // Bank fields
    private String accountHolder;
    private String bankName;
    private String accountNumber;
    // Mobile money fields
    private String mobileHolder;
    private String mobileService;
    private String mobilePhone;
    // Computed at query time
    private long currentBalance;

    public FinancialAccount() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }

    public AccountType getAccountType() { return accountType; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }

    public String getAccountHolder() { return accountHolder; }
    public void setAccountHolder(String accountHolder) { this.accountHolder = accountHolder; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getMobileHolder() { return mobileHolder; }
    public void setMobileHolder(String mobileHolder) { this.mobileHolder = mobileHolder; }

    public String getMobileService() { return mobileService; }
    public void setMobileService(String mobileService) { this.mobileService = mobileService; }

    public String getMobilePhone() { return mobilePhone; }
    public void setMobilePhone(String mobilePhone) { this.mobilePhone = mobilePhone; }

    public long getCurrentBalance() { return currentBalance; }
    public void setCurrentBalance(long currentBalance) { this.currentBalance = currentBalance; }
}
