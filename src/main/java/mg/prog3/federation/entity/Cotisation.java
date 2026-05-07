package mg.prog3.federation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import mg.prog3.federation.enums.TypeCotisation;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cotisation {
    private Long id;
    private TypeCotisation typeCotisation;
    private Long montant;
    private String description;
    private Long collectiviteId;
    private boolean active;
}