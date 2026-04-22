package org.example.tdfinalprog3.model;

import lombok.Data;
import org.example.tdfinalprog3.model.enums.Frequency;
import java.time.LocalDate;

@Data
public class CreateMembershipFeeRequest {
    private LocalDate eligibleFrom;
    private Frequency frequency;
    private double amount;
    private String label;
}