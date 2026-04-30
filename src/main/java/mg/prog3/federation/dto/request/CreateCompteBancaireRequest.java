package mg.prog3.federation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateCompteBancaireRequest {

    @NotBlank(message = "Account holder name is required")
    private String nomTitulaire;

    @NotBlank(message = "Bank name is required")
    private String nomBanque;

    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "^\\d{23}$", message = "Account number must contain exactly 23 digits")
    private String numeroCompteBancaire;
}