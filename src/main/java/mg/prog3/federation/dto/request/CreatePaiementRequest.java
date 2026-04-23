package mg.prog3.federation.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import mg.prog3.federation.enums.ModePaiement;

import java.time.LocalDate;

@Data
public class CreatePaiementRequest {

    @NotNull(message = "Member ID is required")
    private Long membreId;

    @NotNull(message = "Membership fee ID is required")
    private Long cotisationId;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Long montant;

    @NotNull(message = "Payment date is required")
    private LocalDate dateEncaissement;

    @NotNull(message = "Payment method is required")
    private ModePaiement modePaiement;
}
