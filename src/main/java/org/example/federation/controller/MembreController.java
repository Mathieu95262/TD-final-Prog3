package org.example.federation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.federation.dto.request.AssignIdentityRequest;
import org.example.federation.dto.request.CreateMembreRequest;
import org.example.federation.dto.request.MembreCollectiviteRequest;
import org.example.federation.dto.request.ParrainRequest;
import org.example.federation.dto.response.MembreResponse;
import org.example.federation.service.MembreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MembreController {

    private final MembreService membreService;

    /**
     * Crée un nouveau membre simple
     * POST /members
     */
    @PostMapping
    public ResponseEntity<MembreResponse> creerMembre(@Valid @RequestBody CreateMembreRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(membreService.creerMembre(request));
    }

    /**
     * Enregistre les informations de la CIN d'un membre
     * POST /members/{id}/identity
     */
    @PostMapping("/{id}/identity")
    public ResponseEntity<MembreResponse> assignerIdentite(
            @PathVariable Long id,
            @Valid @RequestBody AssignIdentityRequest request) {
        return ResponseEntity.ok(membreService.assignerIdentite(id, request));
    }

    /**
     * Affecte un membre existant à une collectivité avec un poste précis
     * POST /members/assign-collectivite
     */
    @PostMapping("/assign-collectivite")
    public ResponseEntity<MembreResponse> assignerACollectivite(
            @Valid @RequestBody MembreCollectiviteRequest request) {
        return ResponseEntity.ok(membreService.assignerACollectivite(request));
    }

    /**
     * Enregistre un lien de parrainage entre deux membres
     * POST /members/parrainage
     */
    @PostMapping("/parrainage")
    public ResponseEntity<Void> parrainer(@Valid @RequestBody ParrainRequest request) {
        membreService.parrainer(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Liste tous les membres appartenant à une collectivité spécifique
     * GET /members/collectivite/{collectiviteId}
     */
    @GetMapping("/collectivite/{collectiviteId}")
    public ResponseEntity<List<MembreResponse>> getMembresByCollectivite(
            @PathVariable Long collectiviteId) {
        return ResponseEntity.ok(membreService.getMembresByCollectivite(collectiviteId));
    }
}