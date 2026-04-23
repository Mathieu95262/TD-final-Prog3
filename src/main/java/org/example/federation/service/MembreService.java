package org.example.federation.service;

import lombok.RequiredArgsConstructor;
import org.example.federation.dto.request.AssignIdentityRequest;
import org.example.federation.dto.request.CreateMembreRequest;
import org.example.federation.dto.request.MembreCollectiviteRequest;
import org.example.federation.dto.request.ParrainRequest;
import org.example.federation.dto.response.MembreResponse;
import org.example.federation.entity.Collectivite;
import org.example.federation.entity.Membre;
import org.example.federation.entity.Parrainage;
import org.example.federation.enums.Poste;
import org.example.federation.exception.ConflictException;
import org.example.federation.exception.ResourceNotFoundException;
import org.example.federation.repository.CollectiviteRepository;
import org.example.federation.repository.MembreRepository;
import org.example.federation.repository.ParrainageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembreService {

    private final MembreRepository membreRepository;
    private final CollectiviteRepository collectiviteRepository;
    private final ParrainageRepository parrainageRepository; // <--- Injection de la dépendance

    /**
     * Crée un nouveau membre (souvent sans collectivité au départ).
     */
    @Transactional
    public MembreResponse creerMembre(CreateMembreRequest req) {
        if (membreRepository.existsByEmail(req.getEmail())) {
            throw new ConflictException("Un membre avec cet email existe déjà.");
        }

        Membre membre = Membre.builder()
                .nom(req.getNom())
                .prenom(req.getPrenom())
                .email(req.getEmail())
                .genre(req.getGenre())
                .dateNaissance(req.getDateNaissance())
                .poste(Poste.SIMPLE_MEMBRE) // Poste par défaut
                .build();

        return toResponse(membreRepository.save(membre));
    }

    @Transactional
    public MembreResponse assignerIdentite(Long membreId, AssignIdentityRequest req) {
        Membre membre = findMembre(membreId);
        membre.setCin(req.getCin());
        membre.setDateDelivranceCin(req.getDateDelivrance());
        membre.setLieuDelivranceCin(req.getLieuDelivrance());

        return toResponse(membreRepository.save(membre));
    }


    @Transactional
    public MembreResponse assignerACollectivite(MembreCollectiviteRequest req) {
        Membre membre = findMembre(req.getMembreId());
        Collectivite collectivite = collectiviteRepository.findById(req.getCollectiviteId())
                .orElseThrow(() -> new ResourceNotFoundException("Collectivité introuvable"));

        if (req.getPoste() == Poste.PRESIDENT || req.getPoste() == Poste.TRESORIER) {
            if (membreRepository.existsByCollectiviteIdAndPoste(req.getCollectiviteId(), req.getPoste())) {
                throw new ConflictException("La collectivité a déjà un " + req.getPoste());
            }
        }

        membre.setCollectivite(collectivite);
        membre.setPoste(req.getPoste());

        return toResponse(membreRepository.save(membre));
    }


    @Transactional
    public void parrainer(ParrainRequest req) {
        Membre parrain = findMembre(req.getParrainId());
        Membre filleul = findMembre(req.getFilleulId());

        Parrainage nouveauParrainage = Parrainage.builder()
                .parrain(parrain)
                .filleul(filleul)
                .relation(req.getRelation())
                .build();

        parrainageRepository.save(nouveauParrainage);
    }


    @Transactional(readOnly = true)
    public List<MembreResponse> getMembresByCollectivite(Long collectiviteId) {
        return membreRepository.findByCollectiviteId(collectiviteId)
                .stream().map(this::toResponse).toList();
    }


    private Membre findMembre(Long id) {
        return membreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membre introuvable : " + id));
    }

    private MembreResponse toResponse(Membre m) {
        return MembreResponse.builder()
                .id(m.getId())
                .nom(m.getNom())
                .prenom(m.getPrenom())
                .email(m.getEmail())
                .poste(m.getPoste())
                .collectiviteId(m.getCollectivite() != null ? m.getCollectivite().getId() : null)
                .build();
    }
}