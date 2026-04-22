package org.example.tdfinalprog3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tdfinalprog3.model.enums.Frequency;
import org.example.tdfinalprog3.model.enums.ActivityStatus;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MembershipFee {
    private String id;
    private LocalDate eligibleFrom;
    private Frequency frequency;
    private double amount;
    private String label;
    private ActivityStatus status;
    private String collectivityId;

    public static MembershipFee fromCreateRequest(CreateMembershipFeeRequest request, String collectivityId) {
        MembershipFee fee = new MembershipFee();
        fee.setId(UUID.randomUUID().toString());
        fee.setEligibleFrom(request.getEligibleFrom());
        fee.setFrequency(request.getFrequency());
        fee.setAmount(request.getAmount());
        fee.setLabel(request.getLabel());
        fee.setStatus(ActivityStatus.ACTIVE);
        fee.setCollectivityId(collectivityId);
        return fee;
    }
}