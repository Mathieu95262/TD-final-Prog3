package mg.prog3.federation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ParrainRequest {

    @NotNull(message = "Sponsor ID is required")
    private Long parrainId;

    @NotBlank(message = "Relationship type is required")
    private String relation;
}