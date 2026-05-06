package mg.prog3.federation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.prog3.federation.enums.ModePaiement;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paiement {
    private Long id;
    private Long montant;
    private LocalDate dateEncaissement;
    private ModePaiement modePaiement;
    private Long membreId;
    private Long cotisationId;
}