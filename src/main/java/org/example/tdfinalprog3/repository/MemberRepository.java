package org.example.tdfinalprog3.repository;

import org.example.tdfinalprog3.model.Member;
import org.example.tdfinalprog3.model.enums.Gender;
import org.example.tdfinalprog3.model.enums.MemberOccupation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {

    private final JdbcTemplate jdbcTemplate;

    public MemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Member save(Member member) {
        String sql = "INSERT INTO members (id, first_name, last_name, birth_date, gender, address, " +
                "profession, phone_number, email, occupation, adhesion_date, collectivity_id, " +
                "registration_fee_paid, membership_dues_paid) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET " +
                "first_name = EXCLUDED.first_name, last_name = EXCLUDED.last_name, " +
                "birth_date = EXCLUDED.birth_date, gender = EXCLUDED.gender, " +
                "address = EXCLUDED.address, profession = EXCLUDED.profession, " +
                "phone_number = EXCLUDED.phone_number, email = EXCLUDED.email, " +
                "occupation = EXCLUDED.occupation, collectivity_id = EXCLUDED.collectivity_id, " +
                "registration_fee_paid = EXCLUDED.registration_fee_paid, " +
                "membership_dues_paid = EXCLUDED.membership_dues_paid";

        try {
            jdbcTemplate.update(sql,
                    member.getId(),
                    member.getFirstName(),
                    member.getLastName(),
                    member.getBirthDate(),
                    member.getGender() != null ? member.getGender() : null,
                    member.getAddress(),
                    member.getProfession(),
                    member.getPhoneNumber(),
                    member.getEmail(),
                    member.getOccupation() != null ? member.getOccupation() : null,
                    member.getAdhesionDate(),
                    member.getCollectivityId(),
                    member.isRegistrationFeePaid(),
                    member.isMembershipDuesPaid()
            );
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return member;
    }

    public Optional<Member> findById(String id) {
        String sql = "SELECT * FROM members WHERE id = ?";
        List<Member> members = jdbcTemplate.query(sql, new MemberRowMapper(), id);
        return members.isEmpty() ? Optional.empty() : Optional.of(members.get(0));
    }

    public List<Member> findAll() {
        String sql = "SELECT * FROM members";
        return jdbcTemplate.query(sql, new MemberRowMapper());
    }

    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) FROM members WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    public void deleteById(String id) {
        String sql = "DELETE FROM members WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


    public List<Member> findByCollectivityId(String collectivityId) {
        String sql = "SELECT * FROM members WHERE collectivity_id = ?";
        return jdbcTemplate.query(sql, new MemberRowMapper(), collectivityId);
    }


    public List<Member> findSeniorsByCollectivityId(String collectivityId) {
        String sql = "SELECT * FROM members WHERE collectivity_id = ? " +
                "AND occupation IN ('SENIOR', 'PRESIDENT', 'VICE_PRESIDENT', 'TREASURER', 'SECRETARY')";
        return jdbcTemplate.query(sql, new MemberRowMapper(), collectivityId);
    }


    public List<Member> findSeniorsWithMinAdhesionDate(LocalDate minDate) {
        String sql = "SELECT * FROM members WHERE adhesion_date < ? " +
                "AND occupation IN ('SENIOR', 'PRESIDENT', 'VICE_PRESIDENT', 'TREASURER', 'SECRETARY')";
        return jdbcTemplate.query(sql, new MemberRowMapper(), minDate);
    }


    public List<Member> findByOccupation(String occupation) {
        String sql = "SELECT * FROM members WHERE occupation = ?";
        return jdbcTemplate.query(sql, new MemberRowMapper(), occupation);
    }


    public Optional<Member> findByEmail(String email) {
        String sql = "SELECT * FROM members WHERE email = ?";
        List<Member> members = jdbcTemplate.query(sql, new MemberRowMapper(), email);
        return members.isEmpty() ? Optional.empty() : Optional.of(members.get(0));
    }


    public Optional<Member> findByPhoneNumber(String phoneNumber) {
        String sql = "SELECT * FROM members WHERE phone_number = ?";
        List<Member> members = jdbcTemplate.query(sql, new MemberRowMapper(), phoneNumber);
        return members.isEmpty() ? Optional.empty() : Optional.of(members.get(0));
    }


    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM members WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }


    public boolean existsByPhoneNumber(String phoneNumber) {
        String sql = "SELECT COUNT(*) FROM members WHERE phone_number = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, phoneNumber);
        return count != null && count > 0;
    }


    public int countByCollectivityId(String collectivityId) {
        String sql = "SELECT COUNT(*) FROM members WHERE collectivity_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, collectivityId);
        return count != null ? count : 0;
    }


    public List<Member> findActiveMembers() {
        String sql = "SELECT * FROM members WHERE collectivity_id IS NOT NULL";
        return jdbcTemplate.query(sql, new MemberRowMapper());
    }


    public List<Member> findNewMembersBetweenDates(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM members WHERE adhesion_date BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, new MemberRowMapper(), startDate, endDate);
    }

    /**
     * Met à jour uniquement la collectivité d'un membre
     */
    public void updateCollectivityId(String memberId, String collectivityId) {
        String sql = "UPDATE members SET collectivity_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, collectivityId, memberId);
    }

    /**
     * Met à jour le statut de paiement des frais d'adhésion
     */
    public void updateRegistrationFeeStatus(String memberId, boolean paid) {
        String sql = "UPDATE members SET registration_fee_paid = ? WHERE id = ?";
        jdbcTemplate.update(sql, paid, memberId);
    }


    public void updateMembershipDuesStatus(String memberId, boolean paid) {
        String sql = "UPDATE members SET membership_dues_paid = ? WHERE id = ?";
        jdbcTemplate.update(sql, paid, memberId);
    }

    private static class MemberRowMapper implements RowMapper<Member> {
        @Override
        public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
            Member member = new Member();
            member.setId(rs.getString("id"));
            member.setFirstName(rs.getString("first_name"));
            member.setLastName(rs.getString("last_name"));
            member.setBirthDate(rs.getDate("birth_date") != null ?
                    rs.getDate("birth_date").toLocalDate() : null);

            String genderStr = rs.getString("gender");
            member.setGender(genderStr != null ? String.valueOf(Gender.valueOf(genderStr)) : null);

            member.setAddress(rs.getString("address"));
            member.setProfession(rs.getString("profession"));
            member.setPhoneNumber(rs.getString("phone_number"));
            member.setEmail(rs.getString("email"));

            String occupationStr = rs.getString("occupation");
            member.setOccupation(occupationStr != null ? String.valueOf(MemberOccupation.valueOf(occupationStr)) : null);

            member.setAdhesionDate(rs.getDate("adhesion_date") != null ?
                    rs.getDate("adhesion_date").toLocalDate() : null);
            member.setCollectivityId(rs.getString("collectivity_id"));
            member.setRegistrationFeePaid(rs.getBoolean("registration_fee_paid"));
            member.setMembershipDuesPaid(rs.getBoolean("membership_dues_paid"));
            return member;
        }
    }
}