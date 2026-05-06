package hei.school.agriculturalFederation.model;

public class AttendanceRecord {
    private String activityId;
    private String memberId;
    private AttendanceStatus status;
    private String excuseReason;

    public AttendanceRecord() {}

    public String getActivityId() { return activityId; }
    public void setActivityId(String activityId) { this.activityId = activityId; }

    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }

    public AttendanceStatus getStatus() { return status; }
    public void setStatus(AttendanceStatus status) { this.status = status; }

    public String getExcuseReason() { return excuseReason; }
    public void setExcuseReason(String excuseReason) { this.excuseReason = excuseReason; }
}
