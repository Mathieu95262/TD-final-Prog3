package org.example.federation.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.example.federation.enums.TypeCotisation;

@Data
public class CreateCotisationRequest {

    @NotNull(message = "Le type de cotisation est obligatoire")
    private TypeCotisation typeCotisation;

    @NotNull(message = "Le montant est obligatoire")
    @Positive(message = "Le montant doit être positif")
    private Long montant;

    private String description;
}