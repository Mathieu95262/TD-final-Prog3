package mg.prog3.federation.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class ActiviteResponse {
    private Long id;
    private String titre;
    private String description;
    private LocalDate dateActivite;
    private String typeActivite;
    private boolean obligatoire;
    private Long collectiviteId;
    private String collectiviteNom;
}