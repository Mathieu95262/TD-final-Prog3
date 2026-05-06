package hei.school.agriculturalFederation.repository;

import hei.school.agriculturalFederation.datasource.DataSourceConfig;
import hei.school.agriculturalFederation.model.AttendanceRecord;
import hei.school.agriculturalFederation.model.AttendanceStatus;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AttendanceRepository {

    private final DataSourceConfig dataSourceConfig;

    public AttendanceRepository(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    private AttendanceRecord mapRow(ResultSet rs) throws SQLException {
        AttendanceRecord r = new AttendanceRecord();
        r.setActivityId(rs.getString("activity_id"));
        r.setMemberId(rs.getString("member_id"));
        r.setStatus(AttendanceStatus.valueOf(rs.getString("status")));
        r.setExcuseReason(rs.getString("excuse_reason"));
        return r;
    }

    public AttendanceRecord save(AttendanceRecord record) {
        String sql = """
                INSERT INTO activity_attendance (activity_id, member_id, status, excuse_reason)
                VALUES (?, ?, CAST(? AS attendance_status_enum), ?)
                """;
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, record.getActivityId());
            ps.setString(2, record.getMemberId());
            ps.setString(3, record.getStatus().name());
            ps.setString(4, record.getExcuseReason());
            ps.executeUpdate();
            return record;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving attendance record: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    /** Check if a member already has an attendance record for this activity (immutable once set) */
    public boolean existsByActivityAndMember(String activityId, String memberId) {
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT 1 FROM activity_attendance WHERE activity_id = ? AND member_id = ?")) {
            ps.setString(1, activityId);
            ps.setString(2, memberId);
            return ps.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException("Error checking attendance: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    public List<AttendanceRecord> findByActivityId(String activityId) {
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM activity_attendance WHERE activity_id = ?")) {
            ps.setString(1, activityId);
            ResultSet rs = ps.executeQuery();
            List<AttendanceRecord> list = new ArrayList<>();
            while (rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding attendance for activity: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    /** Count total mandatory activities a member was expected to attend in a period */
    public long countMandatoryActivitiesForMember(String memberId, String collectivityId,
                                                   java.time.LocalDate from, java.time.LocalDate to) {
        // Mandatory = activities where attendance_requirement = 'ALL',
        // or 'JUNIORS_ONLY' if member is JUNIOR (we simplify: check member occupation)
        String sql = """
                SELECT COUNT(*) FROM activity a
                WHERE a.collectivity_id = ?
                  AND a.activity_date >= ?
                  AND a.activity_date <= ?
                  AND (
                    a.attendance_requirement = 'ALL'
                    OR (a.attendance_requirement = 'JUNIORS_ONLY'
                        AND EXISTS (SELECT 1 FROM member m WHERE m.id = ? AND m.occupation = 'JUNIOR'))
                  )
                """;
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));
            ps.setString(4, memberId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong(1);
            return 0L;
        } catch (SQLException e) {
            throw new RuntimeException("Error counting mandatory activities: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    /** Count how many of those mandatory activities the member was PRESENT for */
    public long countPresentForMember(String memberId, String collectivityId,
                                      java.time.LocalDate from, java.time.LocalDate to) {
        String sql = """
                SELECT COUNT(*) FROM activity_attendance aa
                JOIN activity a ON a.id = aa.activity_id
                WHERE aa.member_id = ?
                  AND a.collectivity_id = ?
                  AND a.activity_date >= ?
                  AND a.activity_date <= ?
                  AND aa.status = 'PRESENT'
                  AND (
                    a.attendance_requirement = 'ALL'
                    OR (a.attendance_requirement = 'JUNIORS_ONLY'
                        AND EXISTS (SELECT 1 FROM member m WHERE m.id = ? AND m.occupation = 'JUNIOR'))
                  )
                """;
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, memberId);
            ps.setString(2, collectivityId);
            ps.setDate(3, Date.valueOf(from));
            ps.setDate(4, Date.valueOf(to));
            ps.setString(5, memberId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong(1);
            return 0L;
        } catch (SQLException e) {
            throw new RuntimeException("Error counting present activities: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }
}
