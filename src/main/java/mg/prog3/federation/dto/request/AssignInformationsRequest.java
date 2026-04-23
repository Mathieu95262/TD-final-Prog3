package mg.prog3.federation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AssignInformationsRequest {

    @NotBlank(message = "Number is required")
    private String numero;

    @NotBlank(message = "Name is required")
    private String nom;
}
