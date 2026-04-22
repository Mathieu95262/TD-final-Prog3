package org.example.tdfinalprog3.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate eligibleFrom;

    private Frequency frequency;
    private double amount;
    private String label;
    private ActivityStatus status;
    private String collectivityId;

    public static MembershipFee create(String collectivityId, LocalDate eligibleFrom,
                                       Frequency frequency, double amount, String label) {
        MembershipFee fee = new MembershipFee();
        fee.id = UUID.randomUUID().toString();
        fee.collectivityId = collectivityId;
        fee.eligibleFrom = eligibleFrom;
        fee.frequency = frequency;
        fee.amount = amount;
        fee.label = label;
        fee.status = ActivityStatus.ACTIVE;
        return fee;
    }
}