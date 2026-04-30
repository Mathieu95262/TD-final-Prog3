package mg.prog3.federation.dto.response;

import lombok.Builder;
import lombok.Data;
import mg.prog3.federation.enums.ModePaiement;

import java.time.LocalDate;

@Data
@Builder
public class PaiementResponse {
    private Long id;
    private Long montant;
    private LocalDate dateEncaissement;
    private ModePaiement modePaiement;
    private Long membreId;
    private String membreNomPrenom;
    private Long cotisationId;
    private String cotisationDescription;
}