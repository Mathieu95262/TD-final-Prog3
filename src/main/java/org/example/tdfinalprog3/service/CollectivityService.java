package org.example.tdfinalprog3.service;

import org.example.tdfinalprog3.exception.BadRequestException;
import org.example.tdfinalprog3.exception.ResourceNotFoundException;
import org.example.tdfinalprog3.model.*;
import org.example.tdfinalprog3.repository.CollectivityRepository;
import org.example.tdfinalprog3.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CollectivityService {

    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;

    public CollectivityService(CollectivityRepository collectivityRepository,
                               MemberRepository memberRepository) {
        this.collectivityRepository = collectivityRepository;
        this.memberRepository = memberRepository;
    }

    public List<Collectivity> createCollectivities(List<CreateCollectivityRequest> requests) {
        List<Collectivity> responses = new ArrayList<>();

        for (CreateCollectivityRequest request : requests) {
            validateCreateRequest(request);
            Collectivity collectivity = mapToEntity(request);

            // Rassembler tous les IDs membres (structure + liste)
            Set<String> allMemberIds = new HashSet<>(request.getMembers());
            if (request.getStructure() != null) {
                addIfNotNull(allMemberIds, request.getStructure().getPresident());
                addIfNotNull(allMemberIds, request.getStructure().getVicePresident());
                addIfNotNull(allMemberIds, request.getStructure().getTreasurer());
                addIfNotNull(allMemberIds, request.getStructure().getSecretary());
            }

            // Vérifier que tous les membres existent
            for (String memberId : allMemberIds) {
                if (!memberRepository.existsById(memberId)) {
                    throw new ResourceNotFoundException("Member not found with ID: " + memberId);
                }
            }

            // Vérifier les conditions d'ouverture (Section A)
            validateOpeningConditions(allMemberIds);

            // Associer les membres à la collectivité
            for (String memberId : allMemberIds) {
                Member member = memberRepository.findById(memberId).get();
                member.setCollectivityId(collectivity.getId());
                memberRepository.save(member);
            }

            Collectivity saved = collectivityRepository.save(collectivity);
            responses.add(saved);
        }

        return responses;
    }

    public Collectivity assignIdentity(String collectivityId, AssignCollectivityIdentityRequest request) {
        // 1. Vérifier que la collectivité existe
        Collectivity collectivity = collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Collectivity not found with ID: " + collectivityId));

        // 2. Vérifier et attribuer le numéro
        if (request.getNumber() != null && !request.getNumber().trim().isEmpty()) {
            if (collectivity.getNumber() != null && !collectivity.getNumber().isEmpty()) {
                throw new BadRequestException(
                        "The number has already been assigned to this collectivity and cannot be changed.");
            }
            // Vérifier l'unicité du numéro
            if (collectivityRepository.existsById(request.getNumber())) {
                throw new BadRequestException(
                        "The number '" + request.getNumber() + "' is already used by another collectivity.");
            }
            collectivity.setNumber(request.getNumber());
        }

        // 3. Vérifier et attribuer le nom
        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            if (collectivity.getName() != null && !collectivity.getName().isEmpty()) {
                throw new BadRequestException(
                        "The name has already been assigned to this collectivity and cannot be changed.");
            }
            // Vérifier l'unicité du nom
            if (collectivityRepository.existsByName(request.getName())) {
                throw new BadRequestException(
                        "The name '" + request.getName() + "' is already used by another collectivity.");
            }
            collectivity.setName(request.getName());
        }

        // 4. Persister et retourner
        return collectivityRepository.save(collectivity);
    }

    public List<Collectivity> getAllCollectivities() {
        return collectivityRepository.findAll();
    }

    public Collectivity getCollectivityById(String id) {
        return collectivityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collectivity not found with ID: " + id));
    }

    public List<Member> getMembersByCollectivityId(String collectivityId) {
        // Vérifier que la collectivité existe
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new ResourceNotFoundException("Collectivity not found with ID: " + collectivityId);
        }
        return memberRepository.findByCollectivityId(collectivityId);
    }

    private void validateCreateRequest(CreateCollectivityRequest request) {
        if (!request.isFederationApproval()) {
            throw new BadRequestException("Collectivity must have federation approval.");
        }
        if (request.getStructure() == null ||
                request.getStructure().getPresident() == null ||
                request.getStructure().getVicePresident() == null ||
                request.getStructure().getTreasurer() == null ||
                request.getStructure().getSecretary() == null) {
            throw new BadRequestException(
                    "Collectivity must have a complete structure (president, vicePresident, treasurer, secretary).");
        }
        if (request.getLocation() == null || request.getLocation().trim().isEmpty()) {
            throw new BadRequestException("Collectivity location is required.");
        }
        if (request.getSpecialty() == null || request.getSpecialty().trim().isEmpty()) {
            throw new BadRequestException("Collectivity specialty is required.");
        }
        if (request.getMembers() == null || request.getMembers().isEmpty()) {
            throw new BadRequestException("Collectivity must have at least one member.");
        }
    }

    private void validateOpeningConditions(Set<String> memberIds) {
        // Condition 1 : Au moins 10 membres
        if (memberIds.size() < 10) {
            throw new BadRequestException(
                    "Collectivity must have at least 10 members (current: " + memberIds.size() + ").");
        }

        // Condition 2 : Au moins 5 membres avec ancienneté ≥ 6 mois dans la fédération
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        long seniorCount = memberIds.stream()
                .map(memberRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(m -> m.getAdhesionDate() != null
                        && m.getAdhesionDate().isBefore(sixMonthsAgo))
                .count();

        if (seniorCount < 5) {
            throw new BadRequestException(
                    "Collectivity must have at least 5 members with 6+ months seniority (current: "
                            + seniorCount + ").");
        }
    }

    private Collectivity mapToEntity(CreateCollectivityRequest request) {
        Collectivity collectivity = new Collectivity();
        collectivity.setLocation(request.getLocation());
        collectivity.setSpecialty(request.getSpecialty());
        collectivity.setFederationApproval(request.isFederationApproval());
        collectivity.setMemberIds(new ArrayList<>(request.getMembers()));

        Collectivity.CollectivityStructure structure = new Collectivity.CollectivityStructure();
        structure.setPresidentId(request.getStructure().getPresident());
        structure.setVicePresidentId(request.getStructure().getVicePresident());
        structure.setTreasurerId(request.getStructure().getTreasurer());
        structure.setSecretaryId(request.getStructure().getSecretary());
        collectivity.setStructure(structure);

        return collectivity;
    }

    private void addIfNotNull(Set<String> set, String value) {
        if (value != null && !value.trim().isEmpty()) {
            set.add(value);
        }
    }
}