package org.example.federation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ParrainRequest {

    @NotNull(message = "L'identifiant du parrain est obligatoire")
    private Long parrainId;

    @NotBlank(message = "La nature de la relation est obligatoire")
    private String relation;

    public Long getFilleulId() {
    }
}
