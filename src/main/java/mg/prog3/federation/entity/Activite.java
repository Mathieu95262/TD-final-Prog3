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
public class Activite {
    private Long id;
    private String titre;
    private String description;
    private LocalDate dateActivite;
    private String typeActivite;  // OBLIGATOIRE ou EXCEPTIONNELLE
    private boolean obligatoire;
    private Long collectiviteId;
}