package hei.school.agriculturalFederation.repository;

import hei.school.agriculturalFederation.datasource.DataSourceConfig;
import hei.school.agriculturalFederation.model.Activity;
import hei.school.agriculturalFederation.model.ActivityType;
import hei.school.agriculturalFederation.model.AttendanceRequirement;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ActivityRepository {

    private final DataSourceConfig dataSourceConfig;

    public ActivityRepository(DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }

    private Activity mapRow(ResultSet rs) throws SQLException {
        Activity a = new Activity();
        a.setId(rs.getString("id"));
        a.setCollectivityId(rs.getString("collectivity_id"));
        a.setTitle(rs.getString("title"));
        a.setActivityDate(rs.getObject("activity_date", LocalDate.class));
        a.setActivityType(ActivityType.valueOf(rs.getString("activity_type")));
        a.setAttendanceRequirement(AttendanceRequirement.valueOf(rs.getString("attendance_requirement")));
        return a;
    }

    public Activity save(Activity activity) {
        String sql = """
                INSERT INTO activity (id, collectivity_id, title, activity_date, activity_type, attendance_requirement)
                VALUES (?, ?, ?, ?, CAST(? AS activity_type_enum), CAST(? AS attendance_requirement_enum))
                """;
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, activity.getId());
            ps.setString(2, activity.getCollectivityId());
            ps.setString(3, activity.getTitle());
            ps.setDate(4, Date.valueOf(activity.getActivityDate()));
            ps.setString(5, activity.getActivityType().name());
            ps.setString(6, activity.getAttendanceRequirement().name());
            ps.executeUpdate();
            return activity;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving activity: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    public List<Activity> findAllByCollectivityId(String collectivityId) {
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM activity WHERE collectivity_id = ? ORDER BY activity_date DESC")) {
            ps.setString(1, collectivityId);
            ResultSet rs = ps.executeQuery();
            List<Activity> list = new ArrayList<>();
            while (rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding activities: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    public Optional<Activity> findById(String id) {
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM activity WHERE id = ?")) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return Optional.of(mapRow(rs));
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding activity by id: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }

    /** Returns activities of a collectivity within a date range */
    public List<Activity> findByCollectivityAndPeriod(String collectivityId, LocalDate from, LocalDate to) {
        String sql = """
                SELECT * FROM activity
                WHERE collectivity_id = ?
                  AND activity_date >= ?
                  AND activity_date <= ?
                ORDER BY activity_date
                """;
        Connection conn = dataSourceConfig.getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, collectivityId);
            ps.setDate(2, Date.valueOf(from));
            ps.setDate(3, Date.valueOf(to));
            ResultSet rs = ps.executeQuery();
            List<Activity> list = new ArrayList<>();
            while (rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("Error finding activities by period: " + e.getMessage(), e);
        } finally {
            dataSourceConfig.closeConnection(conn);
        }
    }
}
