package mg.prog3.federation.service;

import lombok.RequiredArgsConstructor;
import mg.prog3.federation.dto.request.CreateActiviteRequest;
import mg.prog3.federation.dto.request.CreatePresenceRequest;
import mg.prog3.federation.dto.response.ActiviteResponse;
import mg.prog3.federation.dto.response.PresenceResponse;
import mg.prog3.federation.entity.Activite;
import mg.prog3.federation.entity.Presence;
import mg.prog3.federation.exception.BusinessException;
import mg.prog3.federation.exception.ConflictException;
import mg.prog3.federation.exception.ResourceNotFoundException;
import mg.prog3.federation.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActiviteService {

    private final ActiviteRepository activiteRepository;
    private final PresenceRepository presenceRepository;
    private final CollectiviteRepository collectiviteRepository;
    private final MembreRepository membreRepository;

    @Transactional
    public Collection<ActiviteResponse> creerActivites(Long collectiviteId, List<CreateActiviteRequest> requests) {
        if (!collectiviteRepository.existsById(collectiviteId)) {
            throw new ResourceNotFoundException("Collectivite not found: " + collectiviteId);
        }

        List<Activite> activites = new ArrayList<>();
        for (CreateActiviteRequest req : requests) {
            Activite activite = Activite.builder()
                    .titre(req.getTitre())
                    .description(req.getDescription())
                    .dateActivite(req.getDateActivite())
                    .typeActivite(req.getTypeActivite())
                    .obligatoire(req.isObligatoire())
                    .collectiviteId(collectiviteId)
                    .build();
            activites.add(activite);
        }
        activiteRepository.saveAll(activites);

        return activites.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Collection<ActiviteResponse> getActivitesByCollectivite(Long collectiviteId) {
        if (!collectiviteRepository.existsById(collectiviteId)) {
            throw new ResourceNotFoundException("Collectivite not found: " + collectiviteId);
        }
        return activiteRepository.findByCollectiviteId(collectiviteId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional
    public Collection<PresenceResponse> enregistrerPresences(Long collectiviteId, Long activiteId, List<CreatePresenceRequest> requests) {
        if (!collectiviteRepository.existsById(collectiviteId)) {
            throw new ResourceNotFoundException("Collectivite not found: " + collectiviteId);
        }

        Activite activite = activiteRepository.findById(activiteId)
                .orElseThrow(() -> new ResourceNotFoundException("Activite not found: " + activiteId));

        if (!activite.getCollectiviteId().equals(collectiviteId)) {
            throw new BusinessException("Activite does not belong to this collectivite");
        }

        // Vérifier si une présence existe déjà
        List<Presence> presencesExistantes = presenceRepository.findByActiviteId(activiteId);
        if (!presencesExistantes.isEmpty()) {
            throw new ConflictException("Attendance already recorded for this activity");
        }

        List<Presence> presences = new ArrayList<>();
        for (CreatePresenceRequest req : requests) {
            if (!membreRepository.existsById(req.getMembreId())) {
                throw new ResourceNotFoundException("Member not found: " + req.getMembreId());
            }

            Presence presence = Presence.builder()
                    .activiteId(activiteId)
                    .membreId(req.getMembreId())
                    .present(req.getPresent())
                    .excuse(req.getExcuse() != null ? req.getExcuse() : false)
                    .motif(req.getMotif())
                    .build();
            presences.add(presence);
        }
        presenceRepository.saveAll(presences);

        return presences.stream().map(this::toPresenceResponse).toList();
    }

    @Transactional(readOnly = true)
    public Collection<PresenceResponse> getPresencesByActivite(Long collectiviteId, Long activiteId) {
        if (!collectiviteRepository.existsById(collectiviteId)) {
            throw new ResourceNotFoundException("Collectivite not found: " + collectiviteId);
        }
        if (!activiteRepository.existsById(activiteId)) {
            throw new ResourceNotFoundException("Activite not found: " + activiteId);
        }

        return presenceRepository.findByActiviteId(activiteId)
                .stream().map(this::toPresenceResponse).toList();
    }

    private ActiviteResponse toResponse(Activite a) {
        String collectiviteNom = collectiviteRepository.findById(a.getCollectiviteId())
                .map(c -> c.getNom()).orElse(null);

        return ActiviteResponse.builder()
                .id(a.getId())
                .titre(a.getTitre())
                .description(a.getDescription())
                .dateActivite(a.getDateActivite())
                .typeActivite(a.getTypeActivite())
                .obligatoire(a.isObligatoire())
                .collectiviteId(a.getCollectiviteId())
                .collectiviteNom(collectiviteNom)
                .build();
    }

    private PresenceResponse toPresenceResponse(Presence p) {
        String membreName = membreRepository.findById(p.getMembreId())
                .map(m -> m.getNom() + " " + m.getPrenom())
                .orElse("Inconnu");

        return PresenceResponse.builder()
                .id(p.getId())
                .activiteId(p.getActiviteId())
                .membreId(p.getMembreId())
                .membreNomPrenom(membreName)
                .present(p.isPresent())
                .excuse(p.isExcuse())
                .motif(p.getMotif())
                .build();
    }
}