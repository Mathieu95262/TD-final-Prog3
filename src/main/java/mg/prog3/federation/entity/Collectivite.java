package mg.prog3.federation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Collectivite {
    private Long id;
    private String numero;
    private String nom;
    private String ville;
    private String specialiteAgricole;
    private LocalDate dateCreation;
    private boolean autorisationOuverture;
    private Long cotisationAnnuelleObligatoire;
}