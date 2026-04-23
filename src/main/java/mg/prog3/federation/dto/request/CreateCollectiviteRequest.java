package mg.prog3.federation.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CreateCollectiviteRequest {

    @NotBlank(message = "City is required")
    private String ville;

    @NotBlank(message = "Agricultural specialty is required")
    private String specialiteAgricole;

    @NotNull(message = "Member list is required")
    @Size(min = 10, message = "A collectivite must have at least 10 members")
    @Valid
    private List<MembreCollectiviteRequest> membres;

    @NotNull(message = "Annual membership fee amount is required")
    private Long cotisationAnnuelleObligatoire;
}
