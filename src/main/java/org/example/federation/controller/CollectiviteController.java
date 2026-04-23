package org.example.federation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.federation.dto.request.AssignIdentityRequest;
import org.example.federation.dto.request.CreateCollectiviteRequest;
import org.example.federation.dto.response.CollectiviteResponse;
import org.example.federation.service.CollectiviteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collectivities")
@RequiredArgsConstructor
public class CollectiviteController {

    private final CollectiviteService collectiviteService;

    /**
     * Crée une collectivité avec sa liste de membres initiale (min 10 membres)
     * POST /collectivities
     */
    @PostMapping
    public ResponseEntity<CollectiviteResponse> creerCollectivite(
            @Valid @RequestBody CreateCollectiviteRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(collectiviteService.creerCollectivite(request));
    }

    /**
     * Assigne le nom et le numéro officiel à une collectivité (une seule fois)
     * POST /collectivities/{id}/identity
     */
    @PostMapping("/{id}/identity")
    public ResponseEntity<CollectiviteResponse> assignerIdentite(
            @PathVariable Long id,
            @Valid @RequestBody AssignIdentityRequest request) {
        return ResponseEntity.ok(collectiviteService.assignerIdentite(id, request));
    }

    /**
     * Récupère les détails d'une collectivité par son ID
     * GET /collectivities/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<CollectiviteResponse> getCollectivite(@PathVariable Long id) {
        return ResponseEntity.ok(collectiviteService.getCollectivite(id));
    }

    /**
     * Liste toutes les collectivités enregistrées
     * GET /collectivities
     */
    @GetMapping
    public ResponseEntity<List<CollectiviteResponse>> getAllCollectivites() {
        return ResponseEntity.ok(collectiviteService.getAllCollectivites());
    }
}