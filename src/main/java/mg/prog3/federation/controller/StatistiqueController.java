package mg.prog3.federation.controller;

import lombok.RequiredArgsConstructor;
import mg.prog3.federation.dto.response.CollectiviteStatistiqueResponse;
import mg.prog3.federation.dto.response.MembreStatistiqueResponse;
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
    public ResponseEntity<List<MembreStatistiqueResponse>> getStatistiquesCollectivite(
            @PathVariable Long id,
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(statistiqueService.getStatistiquesCollectivite(id, from, to));
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<CollectiviteStatistiqueResponse>> getStatistiquesGlobales(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return ResponseEntity.ok(statistiqueService.getStatistiquesGlobales(from, to));
    }
}