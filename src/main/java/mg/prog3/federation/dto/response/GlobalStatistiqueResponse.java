package mg.prog3.federation.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GlobalStatistiqueResponse {
    private Long collectiviteId;
    private String collectiviteNom;
    private double pourcentageMembresAJour;
    private long nombreNouveauxAdherents;
}