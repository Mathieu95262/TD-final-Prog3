package mg.prog3.federation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class CreateActiviteRequest {
    @NotBlank
    private String titre;

    private String description;

    @NotNull
    private LocalDate dateActivite;

    @NotBlank
    private String typeActivite;

    private boolean obligatoire = true;
}