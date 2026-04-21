package org.example.tdfinalprog3.repository;

import org.example.tdfinalprog3.model.Member;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemberRepository {
    private final Map<String, Member> members = new ConcurrentHashMap<>();

    public Member save(Member member) {
        members.put(member.getId(), member);
        return member;
    }

    public Optional<Member> findById(String id) {
        return Optional.ofNullable(members.get(id));
    }

    public List<Member> findAll() {
        return new ArrayList<>(members.values());
    }

    public List<Member> findByIds(List<String> ids) {
        return ids.stream()
                .map(members::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public boolean existsById(String id) {
        return members.containsKey(id);
    }

    public List<Member> findSeniorsByCollectivityId(String collectivityId) {
        return members.values().stream()
                .filter(m -> m.getCollectivityId() != null && m.getCollectivityId().equals(collectivityId))
                .filter(m -> m.getOccupation() != null &&
                        (m.getOccupation().name().equals("SENIOR") ||
                                m.getOccupation().name().equals("PRESIDENT") ||
                                m.getOccupation().name().equals("VICE_PRESIDENT") ||
                                m.getOccupation().name().equals("TREASURER") ||
                                m.getOccupation().name().equals("SECRETARY")))
                .collect(Collectors.toList());
    }

    public List<Member> findSeniorsWithMinAdhesionDate(LocalDate minDate) {
        return members.values().stream()
                .filter(m -> m.getOccupation() != null &&
                        (m.getOccupation().name().equals("SENIOR") ||
                                m.getOccupation().name().equals("PRESIDENT") ||
                                m.getOccupation().name().equals("VICE_PRESIDENT") ||
                                m.getOccupation().name().equals("TREASURER") ||
                                m.getOccupation().name().equals("SECRETARY")))
                .filter(m -> m.getAdhesionDate() != null && m.getAdhesionDate().isBefore(minDate))
                .collect(Collectors.toList());
    }

    public void deleteById(String id) {
        members.remove(id);
    }
}