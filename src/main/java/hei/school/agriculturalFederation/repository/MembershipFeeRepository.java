package hei.school.agriculturalFederation.repository;

import hei.school.agriculturalFederation.datasource.DataSourceConfig;
import hei.school.agriculturalFederation.model.FeeType;
import hei.school.agriculturalFederation.model.MembershipFee;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MembershipFeeRepository {

    private final DataSourceConfig dataSourceConfig;

    public MembershipFeeRepository(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    private MembershipFee mapRow(ResultSet rs) throws SQLException {
        MembershipFee fee = new MembershipFee();
        fee.setId(rs.getString("id"));
        fee.setCollectivityId(rs.getString("collectivity_id"));
        fee.setLabel(rs.getString("label"));
        fee.setAmount(rs.getLong("amount"));
        fee.setFeeType(FeeType.valueOf(rs.getString("fee_type")));
        fee.setActive(rs.getBoolean("active"));
        fee.setCreatedAt(rs.getObject("created_at", LocalDate.class));
        return fee;
    }

    public MembershipFee save(MembershipFee fee) {
        String sql = """
                INSERT INTO membership_fee (id, collectivity_id, label, amount, fee_type, active, created_at)
                VALUES (?, ?, ?, ?, CAST(? AS fee_type_enum), ?, ?)
                """;
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fee.getId());
            ps.setString(2, fee.getCollectivityId());
            ps.setString(3, fee.getLabel());
            ps.setLong(4, fee.getAmount());
            ps.setString(5, fee.getFeeType().name());
            ps.setBoolean(6, fee.isActive());
            ps.setDate(7, Date.valueOf(fee.getCreatedAt()));
            ps.executeUpdate();
            return fee;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving membership fee: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    public List<MembershipFee> findAllByCollectivityId(String collectivityId) {
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM membership_fee WHERE collectivity_id = ?")) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            List<MembershipFee> fees = new ArrayList<>();
            while (rs.next()) fees.add(mapRow(rs));
            return fees;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding membership fees: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    /** Returns active fees for a collectivity */
    public List<MembershipFee> findActiveByCollectivityId(String collectivityId) {
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM membership_fee WHERE collectivity_id = ? AND active = TRUE")) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            List<MembershipFee> fees = new ArrayList<>();
            while (rs.next()) fees.add(mapRow(rs));
            return fees;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding active membership fees: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    public Optional<MembershipFee> findById(String id) {
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM membership_fee WHERE id = ?")) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapRow(rs));
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding membership fee by id: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }
}
