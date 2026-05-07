package mg.prog3.federation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mg.prog3.federation.dto.request.CreateActiviteRequest;
import mg.prog3.federation.dto.request.CreatePresenceRequest;
import mg.prog3.federation.dto.response.ActiviteResponse;
import mg.prog3.federation.dto.response.PresenceResponse;
import mg.prog3.federation.service.ActiviteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ActiviteController {

    private final ActiviteService activiteService;

    // POST - Créer des activités
    @PostMapping("/collectivities/{id}/activities")
    public ResponseEntity<Collection<ActiviteResponse>> creerActivites(
            @PathVariable Long id,
            @Valid @RequestBody List<CreateActiviteRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(activiteService.creerActivites(id, requests));
    }

    // GET - Lister les activités
    @GetMapping("/collectivities/{id}/activities")
    public ResponseEntity<Collection<ActiviteResponse>> getActivites(
            @PathVariable Long id) {
        return ResponseEntity.ok(activiteService.getActivitesByCollectivite(id));
    }

    // POST - Enregistrer les présences
    @PostMapping("/collectivities/{id}/activities/{activityId}/attendance")
    public ResponseEntity<Collection<PresenceResponse>> enregistrerPresences(
            @PathVariable Long id,
            @PathVariable Long activityId,
            @Valid @RequestBody List<CreatePresenceRequest> requests) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(activiteService.enregistrerPresences(id, activityId, requests));
    }

    // GET - Voir les présences
    @GetMapping("/collectivities/{id}/activities/{activityId}/attendance")
    public ResponseEntity<Collection<PresenceResponse>> getPresences(
            @PathVariable Long id,
            @PathVariable Long activityId) {
        return ResponseEntity.ok(activiteService.getPresencesByActivite(id, activityId));
    }
}