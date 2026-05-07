package mg.prog3.federation.controller;

import lombok.RequiredArgsConstructor;
import mg.prog3.federation.dto.response.CollectiviteStatistiqueResponse;
import mg.prog3.federation.dto.response.GlobalStatistiqueResponse;
import mg.prog3.federation.service.StatistiqueService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/collectivities")
@RequiredArgsConstructor
public class StatistiqueController {

    private final StatistiqueService statistiqueService;

    @GetMapping("/{id}/statistics")
    public ResponseEntity<CollectiviteStatistiqueResponse> getStatistiquesCollectivite(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(statistiqueService.getStatistiquesCollectivite(id, debut, fin));
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<GlobalStatistiqueResponse>> getStatistiquesGlobales(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(statistiqueService.getStatistiquesGlobales(debut, fin));
    }
}