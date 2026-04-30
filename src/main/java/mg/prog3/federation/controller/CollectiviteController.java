package mg.prog3.federation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mg.prog3.federation.dto.request.AssignInformationsRequest;
import mg.prog3.federation.dto.request.CreateCollectiviteRequest;
import mg.prog3.federation.dto.request.CreateCotisationRequest;
import mg.prog3.federation.dto.response.CollectiviteResponse;
import mg.prog3.federation.dto.response.CompteAvecSoldeResponse;
import mg.prog3.federation.dto.response.CotisationResponse;
import mg.prog3.federation.dto.response.PaiementResponse;
import mg.prog3.federation.service.CollectiviteService;
import mg.prog3.federation.service.CompteService;
import mg.prog3.federation.service.CotisationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping("/collectivities")
@RequiredArgsConstructor
public class CollectiviteController {

    private final CollectiviteService collectiviteService;
    private final CotisationService cotisationService;
    private final CompteService compteService;

    @PostMapping
    public ResponseEntity<Collection<CollectiviteResponse>> creerCollectivites(
            @Valid @RequestBody Collection<CreateCollectiviteRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(collectiviteService.creerCollectivites(requests));
    }

    @GetMapping
    public ResponseEntity<Collection<CollectiviteResponse>> getAllCollectivites() {
        return ResponseEntity.ok(collectiviteService.getAllCollectivites());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CollectiviteResponse> getCollectivite(@PathVariable Long id) {
        return ResponseEntity.ok(collectiviteService.getCollectivite(id));
    }

    @PutMapping("/{id}/informations")
    public ResponseEntity<CollectiviteResponse> assignerInformations(
            @PathVariable Long id,
            @Valid @RequestBody AssignInformationsRequest request) {
        return ResponseEntity.ok(collectiviteService.assignerInformations(id, request));
    }

    @PostMapping("/{id}/membershipFees")
    public ResponseEntity<Collection<CotisationResponse>> creerCotisations(
            @PathVariable Long id,
            @Valid @RequestBody Collection<CreateCotisationRequest> requests) {
        Collection<CotisationResponse> responses = requests.stream()
                .map(req -> cotisationService.creerCotisation(id, req))
                .toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(responses);
    }

    @GetMapping("/{id}/membershipFees")
    public ResponseEntity<Collection<CotisationResponse>> getCotisations(
            @PathVariable Long id) {
        return ResponseEntity.ok(cotisationService.getCotisationsByCollectivite(id));
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<Collection<PaiementResponse>> getTransactions(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(cotisationService.getPaiementsByCollectiviteAndPeriode(id, from, to));
    }

    @GetMapping("/{id}/financialAccounts")
    public ResponseEntity<Collection<CompteAvecSoldeResponse>> getFinancialAccounts(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate at) {
        return ResponseEntity.ok(compteService.getFinancialAccounts(id, at));
    }
}