package mg.prog3.federation.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MembreStatistiqueResponse {
    private Long membreId;
    private String membreNom;
    private String membrePrenom;
    private Long montantEncaissement;
    private Long montantImpaye;
}