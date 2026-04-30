package mg.prog3.federation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCompteMobileMoneyRequest {

    @NotBlank(message = "Account holder name is required")
    private String nomTitulaire;

    @NotBlank(message = "Mobile money service is required")
    private String serviceMobileMoney;

    @NotBlank(message = "Phone number is required")
    private String numeroTelephone;
}