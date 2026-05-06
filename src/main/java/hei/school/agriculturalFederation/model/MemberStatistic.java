package hei.school.agriculturalFederation.model;

public class MemberStatistic {
    private String memberId;
    private String firstName;
    private String lastName;
    private long totalCollected;
    private long totalUnpaid;
    // Bonus 2: attendance rate for the period (0.0 - 100.0), null if no activities
    private Double attendanceRate;

    public MemberStatistic() {}

    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public long getTotalCollected() { return totalCollected; }
    public void setTotalCollected(long totalCollected) { this.totalCollected = totalCollected; }

    public long getTotalUnpaid() { return totalUnpaid; }
    public void setTotalUnpaid(long totalUnpaid) { this.totalUnpaid = totalUnpaid; }

    public Double getAttendanceRate() { return attendanceRate; }
    public void setAttendanceRate(Double attendanceRate) { this.attendanceRate = attendanceRate; }
}
