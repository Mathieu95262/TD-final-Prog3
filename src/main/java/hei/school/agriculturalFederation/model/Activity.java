package hei.school.agriculturalFederation.model;

import java.time.LocalDate;

public class Activity {
    private String id;
    private String collectivityId;
    private String title;
    private LocalDate activityDate;
    private ActivityType activityType;
    private AttendanceRequirement attendanceRequirement;

    public Activity() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getActivityDate() { return activityDate; }
    public void setActivityDate(LocalDate activityDate) { this.activityDate = activityDate; }

    public ActivityType getActivityType() { return activityType; }
    public void setActivityType(ActivityType activityType) { this.activityType = activityType; }

    public AttendanceRequirement getAttendanceRequirement() { return attendanceRequirement; }
    public void setAttendanceRequirement(AttendanceRequirement attendanceRequirement) {
        this.attendanceRequirement = attendanceRequirement;
    }
}
