package mg.prog3.federation.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PresenceResponse {
    private Long id;
    private Long activiteId;
    private Long membreId;
    private String membreNomPrenom;
    private boolean present;
    private boolean excuse;
    private String motif;
}