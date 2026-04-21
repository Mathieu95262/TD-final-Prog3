package org.example.tdfinalprog3.service;

import org.example.tdfinalprog3.exception.BadRequestException;
import org.example.tdfinalprog3.exception.ResourceNotFoundException;
import org.example.tdfinalprog3.model.Collectivity;
import org.example.tdfinalprog3.model.CreateMemberRequest;
import org.example.tdfinalprog3.model.Member;
import org.example.tdfinalprog3.model.enums.MemberOccupation;
import org.example.tdfinalprog3.repository.CollectivityRepository;
import org.example.tdfinalprog3.repository.MemberRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final CollectivityRepository collectivityRepository;

    public MemberService(MemberRepository memberRepository,
                         CollectivityRepository collectivityRepository) {
        this.memberRepository = memberRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<Member> createMembers(List<CreateMemberRequest> requests) {
        List<Member> createdMembers = new ArrayList<>();

        for (CreateMemberRequest request : requests) {
            // Valider les conditions d'admission (Section B-2)
            validateAdmissionConditions(request);

            Member member = mapToEntity(request);
            Member saved = memberRepository.save(member);
            createdMembers.add(saved);
        }

        return createdMembers;
    }

    private void validateAdmissionConditions(CreateMemberRequest request) {
        // Vérifier que la collectivité existe
        if (request.getCollectivityIdentifier() == null ||
                !collectivityRepository.existsById(request.getCollectivityIdentifier())) {
            throw new ResourceNotFoundException("Collectivity not found with ID: " + request.getCollectivityIdentifier());
        }

        // Condition 1: Être parrainé par au moins deux membres confirmés
        if (request.getReferees() == null || request.getReferees().size() < 2) {
            throw new BadRequestException("Member must be sponsored by at least 2 confirmed members");
        }

        // Vérifier que tous les parrains existent et sont des membres confirmés
        List<Member> referees = new ArrayList<>();
        for (String refereeId : request.getReferees()) {
            Member referee = memberRepository.findById(refereeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Referee not found with ID: " + refereeId));

            // Vérifier que le parrain est un membre confirmé
            if (!isConfirmedMember(referee)) {
                throw new BadRequestException("Referee " + refereeId + " is not a confirmed member");
            }

            // Vérifier l'ancienneté du parrain (> 90 jours)
            if (referee.getAdhesionDate() == null ||
                    referee.getAdhesionDate().isAfter(LocalDate.now().minusDays(90))) {
                throw new BadRequestException("Referee " + refereeId + " must have at least 90 days of seniority");
            }

            referees.add(referee);
        }

        // Condition 2: Le nombre de parrains de la collectivité cible >= nombre de parrains d'autres collectivités
        String targetCollectivityId = request.getCollectivityIdentifier();
        long refereesFromTargetCollectivity = referees.stream()
                .filter(r -> targetCollectivityId.equals(r.getCollectivityId()))
                .count();
        long refereesFromOtherCollectivities = referees.size() - refereesFromTargetCollectivity;

        if (refereesFromTargetCollectivity < refereesFromOtherCollectivities) {
            throw new BadRequestException(
                    "Number of referees from target collectivity (" + refereesFromTargetCollectivity +
                            ") must be >= referees from other collectivities (" + refereesFromOtherCollectivities + ")"
            );
        }

        // Condition 3: Frais d'adhésion et cotisations payés
        if (!request.isRegistrationFeePaid()) {
            throw new BadRequestException("Registration fee (50,000 MGA) must be paid");
        }
        if (!request.isMembershipDuesPaid()) {
            throw new BadRequestException("Annual membership dues must be paid");
        }

        // Condition 4: Informations personnelles complètes
        validatePersonalInformation(request);

        // Condition 5: Nature de la relation avec chaque parrain
        if (request.getRefereeRelations() == null ||
                request.getRefereeRelations().size() != request.getReferees().size()) {
            throw new BadRequestException("Relationship nature must be provided for each referee");
        }
    }

    private boolean isConfirmedMember(Member member) {
        if (member.getOccupation() == null) return false;
        return member.getOccupation() == MemberOccupation.SENIOR ||
                member.getOccupation() == MemberOccupation.PRESIDENT ||
                member.getOccupation() == MemberOccupation.VICE_PRESIDENT ||
                member.getOccupation() == MemberOccupation.TREASURER ||
                member.getOccupation() == MemberOccupation.SECRETARY;
    }

    private void validatePersonalInformation(CreateMemberRequest request) {
        if (request.getFirstName() == null || request.getFirstName().trim().isEmpty()) {
            throw new BadRequestException("First name is required");
        }
        if (request.getLastName() == null || request.getLastName().trim().isEmpty()) {
            throw new BadRequestException("Last name is required");
        }
        if (request.getBirthDate() == null) {
            throw new BadRequestException("Birth date is required");
        }
        if (request.getGender() == null) {
            throw new BadRequestException("Gender is required");
        }
        if (request.getAddress() == null || request.getAddress().trim().isEmpty()) {
            throw new BadRequestException("Address is required");
        }
        if (request.getProfession() == null || request.getProfession().trim().isEmpty()) {
            throw new BadRequestException("Profession is required");
        }
        if (request.getPhoneNumber() == null || request.getPhoneNumber().trim().isEmpty()) {
            throw new BadRequestException("Phone number is required");
        }
        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new BadRequestException("Email is required");
        }
        if (request.getOccupation() == null) {
            throw new BadRequestException("Occupation is required");
        }
    }

    private Member mapToEntity(CreateMemberRequest request) {
        Member member = new Member();
        member.setFirstName(request.getFirstName());
        member.setLastName(request.getLastName());
        member.setBirthDate(request.getBirthDate());
        member.setGender(request.getGender());
        member.setAddress(request.getAddress());
        member.setProfession(request.getProfession());
        member.setPhoneNumber(request.getPhoneNumber());
        member.setEmail(request.getEmail());
        member.setOccupation(request.getOccupation());
        member.setCollectivityId(request.getCollectivityIdentifier());
        member.setRefereeIds(request.getReferees() != null ? request.getReferees() : new ArrayList<>());
        member.setRegistrationFeePaid(request.isRegistrationFeePaid());
        member.setMembershipDuesPaid(request.isMembershipDuesPaid());

        // Mapper les relations avec les parrains
        if (request.getRefereeRelations() != null) {
            List<Member.RefereeRelation> relations = request.getRefereeRelations().stream()
                    .map(r -> new Member.RefereeRelation(r.getRefereeId(), r.getRelationship()))
                    .collect(Collectors.toList());
            member.setRefereeRelations(relations);
        }

        return member;
    }
}