package hei.school.agriculturalFederation.model;

public class CreateFinancialAccount {
    private AccountType accountType;
    private String accountHolder;
    private String bankName;
    private String accountNumber;
    private String mobileHolder;
    private String mobileService;
    private String mobilePhone;
    private long initialBalance;

    public CreateFinancialAccount() {}

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

    public long getInitialBalance() { return initialBalance; }
    public void setInitialBalance(long initialBalance) { this.initialBalance = initialBalance; }
}
