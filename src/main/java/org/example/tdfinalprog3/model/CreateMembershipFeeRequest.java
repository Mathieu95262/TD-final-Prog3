package org.example.tdfinalprog3.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.example.tdfinalprog3.model.enums.Frequency;

import java.time.LocalDate;

@Data
public class CreateMembershipFeeRequest {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate eligibleFrom;
    private Frequency frequency;
    private double amount;
    private String label;
}