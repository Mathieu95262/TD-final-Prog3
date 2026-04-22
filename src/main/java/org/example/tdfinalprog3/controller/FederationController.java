package org.example.tdfinalprog3.controller;

import org.example.tdfinalprog3.model.*;
import org.example.tdfinalprog3.service.CollectivityService;
import org.example.tdfinalprog3.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class FederationController {

    private final CollectivityService collectivityService;
    private final MemberService memberService;

    public FederationController(CollectivityService collectivityService,
                                MemberService memberService) {
        this.collectivityService = collectivityService;
        this.memberService = memberService;
    }

    // -------------------------------------------------------
    // A - Création de collectivités
    // -------------------------------------------------------

    @PostMapping("/collectivities")
    public ResponseEntity<List<Collectivity>> createCollectivities(
            @RequestBody List<CreateCollectivityRequest> requests) {
        List<Collectivity> created = collectivityService.createCollectivities(requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // -------------------------------------------------------
    // J - Attribution d'un numéro et d'un nom à une collectivité
    // -------------------------------------------------------

    @PatchMapping("/collectivities/{id}/identity")
    public ResponseEntity<Collectivity> assignIdentity(
            @PathVariable String id,
            @RequestBody AssignCollectivityIdentityRequest request) {
        Collectivity updated = collectivityService.assignIdentity(id, request);
        return ResponseEntity.ok(updated);
    }


    @PostMapping("/members")
    public ResponseEntity<List<Member>> createMembers(
            @RequestBody List<CreateMemberRequest> requests) {
        List<Member> created = memberService.createMembers(requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}