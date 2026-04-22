package org.example.tdfinalprog3.model;

import org.example.tdfinalprog3.model.enums.Frequency;
import java.time.LocalDate;
import java.util.UUID;

public class MembershipFee {
    private String id;
    private LocalDate eligibleFrom;
    private Frequency frequency;
    private double amount;
    private String label;
    private boolean active;
    private String collectivityId;

    public MembershipFee() {
        this.id = UUID.randomUUID().toString();
        this.active = true;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public LocalDate getEligibleFrom() { return eligibleFrom; }
    public void setEligibleFrom(LocalDate eligibleFrom) { this.eligibleFrom = eligibleFrom; }
    public Frequency getFrequency() { return frequency; }
    public void setFrequency(Frequency frequency) { this.frequency = frequency; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }
}
