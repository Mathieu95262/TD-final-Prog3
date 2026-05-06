package hei.school.agriculturalFederation.model;

import java.time.LocalDate;

public class MembershipFee {
    private String id;
    private String collectivityId;
    private String label;
    private long amount;
    private FeeType feeType;
    private boolean active;
    private LocalDate createdAt;

    public MembershipFee() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public long getAmount() { return amount; }
    public void setAmount(long amount) { this.amount = amount; }

    public FeeType getFeeType() { return feeType; }
    public void setFeeType(FeeType feeType) { this.feeType = feeType; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public LocalDate getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDate createdAt) { this.createdAt = createdAt; }
}
