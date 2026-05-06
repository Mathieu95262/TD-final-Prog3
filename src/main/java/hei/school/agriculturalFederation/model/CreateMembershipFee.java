package hei.school.agriculturalFederation.model;

public class CreateMembershipFee {
    private String label;
    private long amount;
    private FeeType feeType;

    public CreateMembershipFee() {}

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public long getAmount() { return amount; }
    public void setAmount(long amount) { this.amount = amount; }

    public FeeType getFeeType() { return feeType; }
    public void setFeeType(FeeType feeType) { this.feeType = feeType; }
}
