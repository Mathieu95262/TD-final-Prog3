package mg.prog3.federation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mg.prog3.federation.dto.request.CreateMembreRequest;
import mg.prog3.federation.dto.request.CreatePaiementRequest;
import mg.prog3.federation.dto.response.MembreResponse;
import mg.prog3.federation.dto.response.PaiementResponse;
import mg.prog3.federation.service.MembreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class MembreController {

    private final MembreService membreService;

    @PostMapping("/members")
    public ResponseEntity<Collection<MembreResponse>> admettreMembers(
            @Valid @RequestBody Collection<CreateMembreRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(membreService.admettreMembers(requests));
    }

    @GetMapping("/members/{id}")
    public ResponseEntity<MembreResponse> getMembre(@PathVariable Long id) {
        return ResponseEntity.ok(membreService.getMembre(id));
    }

    @GetMapping("/collectivities/{collectiviteId}/members")
    public ResponseEntity<Collection<MembreResponse>> getMembresByCollectivite(
            @PathVariable Long collectiviteId) {
        return ResponseEntity.ok(membreService.getMembresByCollectivite(collectiviteId));
    }

    @PostMapping("/members/{id}/payments")
    public ResponseEntity<Collection<PaiementResponse>> enregistrerPaiements(
            @PathVariable Long id,
            @Valid @RequestBody Collection<CreatePaiementRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(membreService.enregistrerPaiements(id, requests));
    }
}