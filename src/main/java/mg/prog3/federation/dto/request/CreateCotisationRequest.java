package mg.prog3.federation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import mg.prog3.federation.enums.TypeCotisation;

@Data
public class CreateCotisationRequest {

    @NotNull(message = "Membership fee type is required")
    private TypeCotisation typeCotisation;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Long montant;

    private String description;
}
