package org.example.federation.service;

import lombok.RequiredArgsConstructor;
import org.example.federation.dto.request.AssignIdentityRequest;
import org.example.federation.dto.request.CreateCollectiviteRequest;
import org.example.federation.dto.request.MembreCollectiviteRequest;
import org.example.federation.dto.response.CollectiviteResponse;
import org.example.federation.entity.Collectivite;
import org.example.federation.entity.Membre;
import org.example.federation.enums.Poste;
import org.example.federation.exception.BusinessException;
import org.example.federation.exception.ConflictException;
import org.example.federation.exception.ResourceNotFoundException;
import org.example.federation.repository.CollectiviteRepository;
import org.example.federation.repository.MembreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CollectiviteService {

    private final CollectiviteRepository collectiviteRepository;
    private final MembreRepository membreRepository;

    @Transactional
    public CollectiviteResponse creerCollectivite(CreateCollectiviteRequest request) {

        List<MembreCollectiviteRequest> membresRequest = request.getMembres();
        if (membresRequest.size() < 10) {
            throw new BusinessException("Une collectivité doit avoir au moins 10 membres inscrits.");
        }

        LocalDate seuilAnciennete = LocalDate.now().minusMonths(6);
        long membresAvecAnciennete = membresRequest.stream()
                .filter(m -> m.getDateAdhesionFederation() != null
                        && !m.getDateAdhesionFederation().isAfter(seuilAnciennete))
                .count();

        if (membresAvecAnciennete < 5) {
            throw new BusinessException(
                    "Au moins 5 membres doivent avoir une antériorité d'au moins 6 mois dans la fédération. " +
                            "Actuellement : " + membresAvecAnciennete + " membre(s) éligible(s).");
        }

        validerPostesSpecifiques(membresRequest);

        Collectivite collectivite = Collectivite.builder()
                .ville(request.getVille())
                .specialiteAgricole(request.getSpecialiteAgricole())
                .dateCreation(LocalDate.now())
                .autorisationOuverture(true)
                .cotisationAnnuelleObligatoire(request.getCotisationAnnuelleObligatoire())
                .build();

        collectivite = collectiviteRepository.save(collectivite);


        List<Membre> membres = new ArrayList<>();
        for (MembreCollectiviteRequest mr : membresRequest) {
            if (membreRepository.existsByEmail(mr.getEmail())) {
                throw new ConflictException("Un membre avec l'email " + mr.getEmail() + " existe déjà.");
            }
            Membre membre = Membre.builder()
                    .nom(mr.getNom())
                    .prenom(mr.getPrenom())
                    .dateNaissance(mr.getDateNaissance())
                    .genre(mr.getGenre())
                    .adresse(mr.getAdresse())
                    .metier(mr.getMetier())
                    .telephone(mr.getTelephone())
                    .email(mr.getEmail())
                    .dateAdhesion(mr.getDateAdhesionFederation() != null
                            ? mr.getDateAdhesionFederation()
                            : LocalDate.now())
                    .poste(mr.getPoste())
                    .actif(true)
                    .collectivite(collectivite)
                    .build();
            membres.add(membre);
        }
        membreRepository.saveAll(membres);

        return toResponse(collectivite, membres.size());
    }

    @Transactional
    public CollectiviteResponse assignerIdentite(Long id, AssignIdentityRequest request) {

        Collectivite collectivite = collectiviteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Collectivité introuvable avec l'identifiant : " + id));

        if (collectivite.getNumero() != null) {
            throw new ConflictException(
                    "Le numéro de la collectivité a déjà été attribué et ne peut plus être modifié.");
        }

        if (collectivite.getNom() != null) {
            throw new ConflictException(
                    "Le nom de la collectivité a déjà été attribué et ne peut plus être modifié.");
        }


        if (collectiviteRepository.existsByNumero(request.getNumero())) {
            throw new ConflictException(
                    "Le numéro '" + request.getNumero() + "' est déjà utilisé par une autre collectivité.");
        }


        if (collectiviteRepository.existsByNom(request.getNom())) {
            throw new ConflictException(
                    "Le nom '" + request.getNom() + "' est déjà utilisé par une autre collectivité.");
        }

        collectivite.setNumero(request.getNumero());
        collectivite.setNom(request.getNom());
        collectivite = collectiviteRepository.save(collectivite);

        long nbMembres = membreRepository.countMembresActifs(id);
        return toResponse(collectivite, nbMembres);
    }


    @Transactional(readOnly = true)
    public CollectiviteResponse getCollectivite(Long id) {
        Collectivite collectivite = collectiviteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Collectivité introuvable avec l'identifiant : " + id));
        long nbMembres = membreRepository.countMembresActifs(id);
        return toResponse(collectivite, nbMembres);
    }


    @Transactional(readOnly = true)
    public List<CollectiviteResponse> getAllCollectivites() {
        return collectiviteRepository.findAll().stream()
                .map(c -> toResponse(c, membreRepository.countMembresActifs(c.getId())))
                .toList();
    }

    private void validerPostesSpecifiques(List<MembreCollectiviteRequest> membres) {
        boolean aPresident = membres.stream().anyMatch(m -> m.getPoste() == Poste.PRESIDENT);
        boolean aPresidentAdjoint = membres.stream().anyMatch(m -> m.getPoste() == Poste.PRESIDENT_ADJOINT);
        boolean aTresorier = membres.stream().anyMatch(m -> m.getPoste() == Poste.TRESORIER);
        boolean aSecretaire = membres.stream().anyMatch(m -> m.getPoste() == Poste.SECRETAIRE);

        if (!aPresident) {
            throw new BusinessException("Le poste de président doit être occupé.");
        }
        if (!aPresidentAdjoint) {
            throw new BusinessException("Le poste de président adjoint doit être occupé.");
        }
        if (!aTresorier) {
            throw new BusinessException("Le poste de trésorier doit être occupé.");
        }
        if (!aSecretaire) {
            throw new BusinessException("Le poste de secrétaire doit être occupé.");
        }

        long nbPresidents = membres.stream().filter(m -> m.getPoste() == Poste.PRESIDENT).count();
        long nbPresidentsAdjoints = membres.stream().filter(m -> m.getPoste() == Poste.PRESIDENT_ADJOINT).count();
        long nbTresoriers = membres.stream().filter(m -> m.getPoste() == Poste.TRESORIER).count();
        long nbSecretaires = membres.stream().filter(m -> m.getPoste() == Poste.SECRETAIRE).count();

        if (nbPresidents > 1) throw new BusinessException("Il ne peut y avoir qu'un seul président.");
        if (nbPresidentsAdjoints > 1) throw new BusinessException("Il ne peut y avoir qu'un seul président adjoint.");
        if (nbTresoriers > 1) throw new BusinessException("Il ne peut y avoir qu'un seul trésorier.");
        if (nbSecretaires > 1) throw new BusinessException("Il ne peut y avoir qu'un seul secrétaire.");
    }

    private CollectiviteResponse toResponse(Collectivite collectivite, long nombreMembres) {
        return CollectiviteResponse.builder()
                .id(collectivite.getId())
                .numero(collectivite.getNumero())
                .nom(collectivite.getNom())
                .ville(collectivite.getVille())
                .specialiteAgricole(collectivite.getSpecialiteAgricole())
                .dateCreation(collectivite.getDateCreation())
                .autorisationOuverture(collectivite.isAutorisationOuverture())
                .cotisationAnnuelleObligatoire(collectivite.getCotisationAnnuelleObligatoire())
                .nombreMembres(nombreMembres)
                .build();
    }
}