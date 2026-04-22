package org.example.tdfinalprog3.repository;

import org.example.tdfinalprog3.model.MembershipFee;
import org.example.tdfinalprog3.model.enums.ActivityStatus;
import org.example.tdfinalprog3.model.enums.Frequency;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class MembershipFeeRepository {

    private final JdbcTemplate jdbcTemplate;

    public MembershipFeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public MembershipFee save(MembershipFee fee) {
        String sql = "INSERT INTO membership_fees (id, eligible_from, frequency, amount, label, status, collectivity_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET " +
                "eligible_from = EXCLUDED.eligible_from, frequency = EXCLUDED.frequency, " +
                "amount = EXCLUDED.amount, label = EXCLUDED.label, status = EXCLUDED.status";
        jdbcTemplate.update(sql,
                fee.getId(),
                fee.getEligibleFrom(),
                fee.getFrequency().name(),
                fee.getAmount(),
                fee.getLabel(),
                fee.getStatus().name(),
                fee.getCollectivityId()
        );
        return fee;
    }

    public List<MembershipFee> saveAll(List<MembershipFee> fees) {
        for (MembershipFee fee : fees) {
            save(fee);
        }
        return fees;
    }

    public Optional<MembershipFee> findById(String id) {
        String sql = "SELECT * FROM membership_fees WHERE id = ?";
        List<MembershipFee> fees = jdbcTemplate.query(sql, new MembershipFeeRowMapper(), id);
        return fees.isEmpty() ? Optional.empty() : Optional.of(fees.get(0));
    }

    public List<MembershipFee> findByCollectivityId(String collectivityId) {
        String sql = "SELECT * FROM membership_fees WHERE collectivity_id = ?";
        return jdbcTemplate.query(sql, new MembershipFeeRowMapper(), collectivityId);
    }

    public boolean existsById(String id) {
        String sql = "SELECT COUNT(*) FROM membership_fees WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    private static class MembershipFeeRowMapper implements RowMapper<MembershipFee> {
        @Override
        public MembershipFee mapRow(ResultSet rs, int rowNum) throws SQLException {
            MembershipFee fee = new MembershipFee();
            fee.setId(rs.getString("id"));
            fee.setEligibleFrom(rs.getDate("eligible_from") != null ?
                    rs.getDate("eligible_from").toLocalDate() : null);
            fee.setFrequency(Frequency.valueOf(rs.getString("frequency")));
            fee.setAmount(rs.getDouble("amount"));
            fee.setLabel(rs.getString("label"));
            fee.setStatus(ActivityStatus.valueOf(rs.getString("status")));
            fee.setCollectivityId(rs.getString("collectivity_id"));
            return fee;
        }
    }
}