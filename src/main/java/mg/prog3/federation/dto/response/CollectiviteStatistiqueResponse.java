package mg.prog3.federation.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CollectiviteStatistiqueResponse {
    private Long collectiviteId;
    private String collectiviteNom;
    private List<MembreStatistiqueResponse> membres;
}