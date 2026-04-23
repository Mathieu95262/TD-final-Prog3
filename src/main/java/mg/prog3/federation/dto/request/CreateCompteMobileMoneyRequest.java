package mg.prog3.federation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCompteMobileMoneyRequest {

    @NotBlank(message = "Account holder name is required")
    private String nomTitulaire;

    @NotBlank(message = "Mobile money service is required")
    private String serviceMobileMoney;

    @NotBlank(message = "Phone number is required")
    private String numeroTelephone;

    @NotNull(message = "Initial balance is required")
    private Long soldeInitial;

    private Long collectiviteId;
    private Boolean appartientFederation;
}
