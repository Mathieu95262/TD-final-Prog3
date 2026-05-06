package hei.school.agriculturalFederation.model;

public class CollectivityStatistic {
    private String collectivityId;
    private String collectivityName;
    private double percentageMembersUpToDate;
    private long newMembersCount;
    // Bonus 2: global attendance rate across all activities in the period
    private Double globalAttendanceRate;

    public CollectivityStatistic() {}

    public String getCollectivityId() { return collectivityId; }
    public void setCollectivityId(String collectivityId) { this.collectivityId = collectivityId; }

    public String getCollectivityName() { return collectivityName; }
    public void setCollectivityName(String collectivityName) { this.collectivityName = collectivityName; }

    public double getPercentageMembersUpToDate() { return percentageMembersUpToDate; }
    public void setPercentageMembersUpToDate(double v) { this.percentageMembersUpToDate = v; }

    public long getNewMembersCount() { return newMembersCount; }
    public void setNewMembersCount(long newMembersCount) { this.newMembersCount = newMembersCount; }

    public Double getGlobalAttendanceRate() { return globalAttendanceRate; }
    public void setGlobalAttendanceRate(Double globalAttendanceRate) { this.globalAttendanceRate = globalAttendanceRate; }
}
