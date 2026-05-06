package hei.school.agriculturalFederation.repository;

import hei.school.agriculturalFederation.datasource.DataSourceConfig;
import hei.school.agriculturalFederation.model.MemberPayment;
import hei.school.agriculturalFederation.model.PaymentMode;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberPaymentRepository {

    private final DataSourceConfig dataSourceConfig;

    public MemberPaymentRepository(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    private MemberPayment mapRow(ResultSet rs) throws SQLException {
        MemberPayment p = new MemberPayment();
        p.setId(rs.getString("id"));
        p.setMemberId(rs.getString("member_id"));
        p.setMembershipFeeId(rs.getString("membership_fee_id"));
        p.setAmount(rs.getLong("amount"));
        p.setPaymentDate(rs.getObject("payment_date", LocalDate.class));
        p.setPaymentMode(PaymentMode.valueOf(rs.getString("payment_mode")));
        return p;
    }

    public MemberPayment save(MemberPayment payment) {
        String sql = """
                INSERT INTO member_payment (id, member_id, membership_fee_id, amount, payment_date, payment_mode)
                VALUES (?, ?, ?, ?, ?, CAST(? AS payment_mode_enum))
                """;
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, payment.getId());
            ps.setString(2, payment.getMemberId());
            ps.setString(3, payment.getMembershipFeeId());
            ps.setLong(4, payment.getAmount());
            ps.setDate(5, Date.valueOf(payment.getPaymentDate()));
            ps.setString(6, payment.getPaymentMode().name());
            ps.executeUpdate();
            return payment;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving member payment: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    /**
     * Returns all payments for a given member within [from, to] inclusive.
     */
    public List<MemberPayment> findByMemberIdAndPeriod(String memberId, LocalDate from, LocalDate to) {
        String sql = """
                SELECT * FROM member_payment
                WHERE member_id = ?
                  AND payment_date >= ?
                  AND payment_date <= ?
                """;
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, memberId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            List<MemberPayment> payments = new ArrayList<>();
            while (rs.next()) payments.add(mapRow(rs));
            return payments;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding member payments by period: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    /**
     * Total amount paid by a member for a specific fee within the period.
     */
    public long sumByMemberIdAndFeeIdAndPeriod(String memberId, String feeId, LocalDate from, LocalDate to) {
        String sql = """
                SELECT COALESCE(SUM(amount), 0)
                FROM member_payment
                WHERE member_id = ?
                  AND membership_fee_id = ?
                  AND payment_date >= ?
                  AND payment_date <= ?
                """;
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, memberId);
            ps.setString(2, feeId);
            ps.setDate(3, Date.valueOf(from));
            ps.setDate(4, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong(1);
            return 0L;
        } catch (SQLException e) {
            throw new RuntimeException("Error summing payments: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    /**
     * Total amount collected from a member across all fees within the period.
     */
    public long sumByMemberIdAndPeriod(String memberId, LocalDate from, LocalDate to) {
        String sql = """
                SELECT COALESCE(SUM(amount), 0)
                FROM member_payment
                WHERE member_id = ?
                  AND payment_date >= ?
                  AND payment_date <= ?
                """;
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, memberId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong(1);
            return 0L;
        } catch (SQLException e) {
            throw new RuntimeException("Error summing member payments: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }
}
