package org.example.federation.dto.response;

import lombok.Builder;
import lombok.Data;
import org.example.federation.enums.TypeCotisation;

@Data
@Builder
public class CotisationResponse {
    private Long id;
    private TypeCotisation typeCotisation;
    private Long montant;
    private String description;
    private Long collectiviteId;
    private String collectiviteNom;
}
