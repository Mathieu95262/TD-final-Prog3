package hei.school.agriculturalFederation.service;

import hei.school.agriculturalFederation.exception.BadRequestException;
import hei.school.agriculturalFederation.exception.NotFoundException;
import hei.school.agriculturalFederation.model.AttendanceRecord;
import hei.school.agriculturalFederation.repository.ActivityRepository;
import hei.school.agriculturalFederation.repository.AttendanceRepository;
import hei.school.agriculturalFederation.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final ActivityRepository activityRepository;
    private final MemberRepository memberRepository;

    public AttendanceService(AttendanceRepository attendanceRepository,
                             ActivityRepository activityRepository,
                             MemberRepository memberRepository) {
        this.attendanceRepository = attendanceRepository;
        this.activityRepository = activityRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * POST /collectivities/{id}/activities/{activityId}/attendance
     * Records presence/absence for a list of members.
     * Once a member is marked, it cannot be changed (fraud prevention).
     */
    public List<AttendanceRecord> recordAttendance(String collectivityId,
                                                    String activityId,
                                                    List<AttendanceRecord> records) {
        // Verify the activity exists and belongs to the collectivity
        activityRepository.findById(activityId)
                .filter(a -> a.getCollectivityId().equals(collectivityId))
                .orElseThrow(() -> new NotFoundException(
                        "Activity " + activityId + " not found in collectivity " + collectivityId));

        List<AttendanceRecord> saved = new ArrayList<>();
        for (AttendanceRecord record : records) {
            if (record.getMemberId() == null) {
                throw new BadRequestException("memberId is required in each attendance record.");
            }
            if (record.getStatus() == null) {
                throw new BadRequestException("status is required in each attendance record.");
            }
            // Verify member exists
            memberRepository.findById(record.getMemberId())
                    .orElseThrow(() -> new NotFoundException(
                            "Member not found: " + record.getMemberId()));

            // Immutability rule: once marked, cannot be changed
            if (attendanceRepository.existsByActivityAndMember(activityId, record.getMemberId())) {
                throw new BadRequestException(
                        "Attendance for member " + record.getMemberId()
                                + " has already been recorded and cannot be modified.");
            }

            record.setActivityId(activityId);
            saved.add(attendanceRepository.save(record));
        }
        return saved;
    }

    /**
     * GET /collectivities/{id}/activities/{activityId}/attendance
     * Returns all attendance records for the activity (all statuses).
     */
    public List<AttendanceRecord> getAttendance(String collectivityId, String activityId) {
        activityRepository.findById(activityId)
                .filter(a -> a.getCollectivityId().equals(collectivityId))
                .orElseThrow(() -> new NotFoundException(
                        "Activity " + activityId + " not found in collectivity " + collectivityId));

        return attendanceRepository.findByActivityId(activityId);
    }
}
