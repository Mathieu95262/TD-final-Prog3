package org.example.tdfinalprog3.repository;

import org.example.tdfinalprog3.model.Payment;
import org.example.tdfinalprog3.model.enums.PaymentMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class PaymentRepository {

    private final JdbcTemplate jdbcTemplate;

    public PaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Payment save(Payment payment) {
        String sql = "INSERT INTO payments (id, amount, payment_mode, member_id, membership_fee_id, account_id, payment_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                payment.getId(),
                payment.getAmount(),
                payment.getPaymentMode().name(),
                payment.getMemberId(),
                payment.getMembershipFeeId(),
                payment.getAccountId(),
                payment.getPaymentDate()
        );
        return payment;
    }

    public List<Payment> findByMemberId(String memberId) {
        String sql = "SELECT * FROM payments WHERE member_id = ?";
        return jdbcTemplate.query(sql, new PaymentRowMapper(), memberId);
    }

    public List<Payment> findByAccountIdAndDateBetween(String accountId, LocalDate from, LocalDate to) {
        String sql = "SELECT * FROM payments WHERE account_id = ? AND payment_date BETWEEN ? AND ?";
        return jdbcTemplate.query(sql, new PaymentRowMapper(), accountId, from, to);
    }

    private static class PaymentRowMapper implements RowMapper<Payment> {
        @Override
        public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Payment payment = new Payment();
            payment.setId(rs.getString("id"));
            payment.setAmount(rs.getDouble("amount"));
            payment.setPaymentMode(PaymentMode.valueOf(rs.getString("payment_mode")));
            payment.setMemberId(rs.getString("member_id"));
            payment.setMembershipFeeId(rs.getString("membership_fee_id"));
            payment.setAccountId(rs.getString("account_id"));
            payment.setPaymentDate(rs.getDate("payment_date") != null ? rs.getDate("payment_date").toLocalDate() : null);
            return payment;
        }
    }
}