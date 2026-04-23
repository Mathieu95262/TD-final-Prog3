package mg.prog3.federation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mg.prog3.federation.dto.request.CreateCotisationRequest;
import mg.prog3.federation.dto.response.CotisationResponse;
import mg.prog3.federation.dto.response.PaiementResponse;
import mg.prog3.federation.service.CotisationService;
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

    @PostMapping("/collectivities/{collectiviteId}/membershipFees")
    public ResponseEntity<List<CotisationResponse>> creerCotisations(
            @PathVariable Long collectiviteId,
            @Valid @RequestBody List<CreateCotisationRequest> requests) {
        List<CotisationResponse> responses = requests.stream()
                .map(req -> cotisationService.creerCotisation(collectiviteId, req))
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @GetMapping("/collectivities/{collectiviteId}/membershipFees")
    public ResponseEntity<List<CotisationResponse>> getCotisations(
            @PathVariable Long collectiviteId) {
        return ResponseEntity.ok(cotisationService.getCotisationsByCollectivite(collectiviteId));
    }

    @GetMapping("/collectivities/{collectiviteId}/membershipFees/{cotisationId}")
    public ResponseEntity<CotisationResponse> getCotisation(
            @PathVariable Long collectiviteId,
            @PathVariable Long cotisationId) {
        return ResponseEntity.ok(cotisationService.getCotisationById(cotisationId));
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
}
