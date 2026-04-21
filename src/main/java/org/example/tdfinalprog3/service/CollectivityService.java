package org.example.tdfinalprog3.service;

import org.example.tdfinalprog3.exception.BadRequestException;
import org.example.tdfinalprog3.exception.ResourceNotFoundException;
import org.example.tdfinalprog3.model.Collectivity;
import org.example.tdfinalprog3.model.CreateCollectivityRequest;
import org.example.tdfinalprog3.model.Member;
import org.example.tdfinalprog3.repository.CollectivityRepository;
import org.example.tdfinalprog3.repository.MemberRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;

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
        List<Collectivity> createdCollectivities = new ArrayList<>();

        for (CreateCollectivityRequest request : requests) {
            validateCreateRequest(request);
            Collectivity collectivity = mapToEntity(request);

            // Vérifier les membres
            Set<String> allMemberIds = new HashSet<>(request.getMembers());
            if (request.getStructure() != null) {
                if (request.getStructure().getPresident() != null)
                    allMemberIds.add(request.getStructure().getPresident());
                if (request.getStructure().getVicePresident() != null)
                    allMemberIds.add(request.getStructure().getVicePresident());
                if (request.getStructure().getTreasurer() != null)
                    allMemberIds.add(request.getStructure().getTreasurer());
                if (request.getStructure().getSecretary() != null)
                    allMemberIds.add(request.getStructure().getSecretary());
            }

            // Vérifier que tous les membres existent
            for (String memberId : allMemberIds) {
                if (!memberRepository.existsById(memberId)) {
                    throw new ResourceNotFoundException("Member not found with ID: " + memberId);
                }
            }

            // Vérifier les conditions d'ouverture (Section A)
            validateOpeningConditions(allMemberIds);

            // Mettre à jour les membres avec la nouvelle collectivité
            for (String memberId : allMemberIds) {
                Member member = memberRepository.findById(memberId).get();
                member.setCollectivityId(collectivity.getId());
                memberRepository.save(member);
            }

            Collectivity saved = collectivityRepository.save(collectivity);
            createdCollectivities.add(saved);
        }

        return createdCollectivities;
    }

    private void validateCreateRequest(CreateCollectivityRequest request) {
        if (!request.isFederationApproval()) {
            throw new BadRequestException("Collectivity must have federation approval");
        }
        if (request.getStructure() == null ||
                request.getStructure().getPresident() == null ||
                request.getStructure().getVicePresident() == null ||
                request.getStructure().getTreasurer() == null ||
                request.getStructure().getSecretary() == null) {
            throw new BadRequestException("Collectivity must have complete structure (president, vicePresident, treasurer, secretary)");
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new BadRequestException("Collectivity name is required");
        }
        if (request.getLocation() == null || request.getLocation().trim().isEmpty()) {
            throw new BadRequestException("Collectivity location is required");
        }
        if (request.getSpecialty() == null || request.getSpecialty().trim().isEmpty()) {
            throw new BadRequestException("Collectivity specialty is required");
        }
    }

    private void validateOpeningConditions(Set<String> memberIds) {
        // Condition 1: Au moins 10 membres
        if (memberIds.size() < 10) {
            throw new BadRequestException("Collectivity must have at least 10 members (current: " + memberIds.size() + ")");
        }

        // Condition 2: Au moins 5 membres avec antériorité d'au moins 6 mois
        LocalDate sixMonthsAgo = LocalDate.now().minusMonths(6);
        long seniorCount = memberIds.stream()
                .map(memberRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(m -> m.getAdhesionDate() != null && m.getAdhesionDate().isBefore(sixMonthsAgo))
                .count();

        if (seniorCount < 5) {
            throw new BadRequestException("Collectivity must have at least 5 members with 6+ months seniority (current: " + seniorCount + ")");
        }
    }

    private Collectivity mapToEntity(CreateCollectivityRequest request) {
        Collectivity collectivity = new Collectivity();
        collectivity.setName(request.getName());
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
}