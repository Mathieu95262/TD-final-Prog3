package mg.prog3.federation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePresenceRequest {
    @NotNull
    private Long membreId;

    @NotNull
    private Boolean present;

    private Boolean excuse = false;
    private String motif;
}