package org.example.federation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.federation.dto.request.CreateCotisationRequest;
import org.example.federation.dto.request.CreatePaiementRequest;
import org.example.federation.dto.response.CotisationResponse;
import org.example.federation.dto.response.PaiementResponse;
import org.example.federation.service.CotisationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CotisationController {

    private final CotisationService cotisationService;

    @PostMapping("/collectivities/{collectiviteId}/cotisations")
    public ResponseEntity<CotisationResponse> creerCotisation(
            @PathVariable Long collectiviteId,
            @Valid @RequestBody CreateCotisationRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cotisationService.creerCotisation(collectiviteId, request));
    }


    @GetMapping("/collectivities/{collectiviteId}/cotisations")
    public ResponseEntity<List<CotisationResponse>> getCotisations(
            @PathVariable Long collectiviteId) {
        return ResponseEntity.ok(cotisationService.getCotisationsByCollectivite(collectiviteId));
    }

    @GetMapping("/collectivities/{collectiviteId}/cotisations/{cotisationId}")
    public ResponseEntity<CotisationResponse> getCotisation(
            @PathVariable Long collectiviteId,
            @PathVariable Long cotisationId) {
        return ResponseEntity.ok(cotisationService.getCotisationById(cotisationId));
    }

    @PostMapping("/collectivities/{collectiviteId}/paiements")
    public ResponseEntity<PaiementResponse> enregistrerPaiement(
            @PathVariable Long collectiviteId,
            @Valid @RequestBody CreatePaiementRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cotisationService.enregistrerPaiement(collectiviteId, request));
    }

    @GetMapping("/collectivities/{collectiviteId}/paiements")
    public ResponseEntity<List<PaiementResponse>> getPaiements(
            @PathVariable Long collectiviteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(
                cotisationService.getPaiementsByCollectiviteAndPeriode(collectiviteId, debut, fin));
    }

    @GetMapping("/members/{membreId}/paiements")
    public ResponseEntity<List<PaiementResponse>> getPaiementsByMembre(
            @PathVariable Long membreId) {
        return ResponseEntity.ok(cotisationService.getPaiementsByMembre(membreId));
    }

    @GetMapping("/collectivities/{collectiviteId}/cotisations/{cotisationId}/paiements")
    public ResponseEntity<List<PaiementResponse>> getPaiementsByCotisation(
            @PathVariable Long collectiviteId,
            @PathVariable Long cotisationId) {
        return ResponseEntity.ok(cotisationService.getPaiementsByCotisation(cotisationId));
    }
}