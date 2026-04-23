package org.example.federation.controller;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.example.federation.dto.request.CreateCompteBancaireRequest;
import org.example.federation.dto.request.CreateCompteMobileMoneyRequest;
import org.example.federation.dto.response.CompteResponse;
import org.example.federation.service.CompteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CompteController {

    private final CompteService compteService;

    @PostMapping("/collectivities/{collectiviteId}/comptes/caisse")
    public ResponseEntity<CompteResponse> creerCaisseCollectivite(
            @PathVariable Long collectiviteId,
            @RequestBody Map<String, String> body) {

        String nomTitulaire = body.get("nomTitulaire");
        if (nomTitulaire == null || nomTitulaire.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(compteService.creerCaisseCollectivite(collectiviteId, nomTitulaire));
    }

    @PostMapping("/collectivities/{collectiviteId}/comptes/bancaire")
    public ResponseEntity<CompteResponse> creerCompteBancaireCollectivite(
            @PathVariable Long collectiviteId,
            @Valid @RequestBody CreateCompteBancaireRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(compteService.creerCompteBancaireCollectivite(collectiviteId, request));
    }

    @PostMapping("/collectivities/{collectiviteId}/comptes/mobile-money")
    public ResponseEntity<CompteResponse> creerCompteMobileMoneyCollectivite(
            @PathVariable Long collectiviteId,
            @Valid @RequestBody CreateCompteMobileMoneyRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(compteService.creerCompteMobileMoneyCollectivite(collectiviteId, request));
    }

    @GetMapping("/collectivities/{collectiviteId}/comptes")
    public ResponseEntity<List<CompteResponse>> getComptesByCollectivite(
            @PathVariable Long collectiviteId) {
        return ResponseEntity.ok(compteService.getComptesByCollectivite(collectiviteId));
    }

    @PostMapping("/federation/comptes/caisse")
    public ResponseEntity<CompteResponse> creerCaisseFederation(
            @RequestBody Map<String, String> body) {

        String nomTitulaire = body.get("nomTitulaire");
        if (nomTitulaire == null || nomTitulaire.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(compteService.creerCaisseFederation(nomTitulaire));
    }

    @PostMapping("/federation/comptes/bancaire")
    public ResponseEntity<CompteResponse> creerCompteBancaireFederation(
            @Valid @RequestBody CreateCompteBancaireRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(compteService.creerCompteBancaireFederation(request));
    }

    @PostMapping("/federation/comptes/mobile-money")
    public ResponseEntity<CompteResponse> creerCompteMobileMoneyFederation(
            @Valid @RequestBody CreateCompteMobileMoneyRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(compteService.creerCompteMobileMoneyFederation(request));
    }

    @GetMapping("/federation/comptes")
    public ResponseEntity<List<CompteResponse>> getComptesFederation() {
        return ResponseEntity.ok(compteService.getComptesFederation());
    }

    @GetMapping("/comptes/{id}")
    public ResponseEntity<CompteResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(compteService.getById(id));
    }
}
