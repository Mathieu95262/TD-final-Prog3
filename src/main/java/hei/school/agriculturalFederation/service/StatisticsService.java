package hei.school.agriculturalFederation.service;

import hei.school.agriculturalFederation.exception.NotFoundException;
import hei.school.agriculturalFederation.model.*;
import hei.school.agriculturalFederation.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {

    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;
    private final MembershipFeeRepository membershipFeeRepository;
    private final MemberPaymentRepository memberPaymentRepository;
    private final ActivityRepository activityRepository;
    private final AttendanceRepository attendanceRepository;

    public StatisticsService(CollectivityRepository collectivityRepository,
                             MemberRepository memberRepository,
                             MembershipFeeRepository membershipFeeRepository,
                             MemberPaymentRepository memberPaymentRepository,
                             ActivityRepository activityRepository,
                             AttendanceRepository attendanceRepository) {
        this.collectivityRepository = collectivityRepository;
        this.memberRepository = memberRepository;
        this.membershipFeeRepository = membershipFeeRepository;
        this.memberPaymentRepository = memberPaymentRepository;
        this.activityRepository = activityRepository;
        this.attendanceRepository = attendanceRepository;
    }

    /**
     * GET /collectivities/{id}/statistics?from=DATE&to=DATE
     *
     * Per active member:
     *  - totalCollected: sum of payments in [from, to]
     *  - totalUnpaid: sum of active fees - payments made (min 0)
     *  - attendanceRate: % of mandatory activities attended (Bonus 2, null if no activities)
     */
    public List<MemberStatistic> getCollectivityStatistics(String collectivityId,
                                                           LocalDate from, LocalDate to) {
        collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException(
                        "Collectivity not found: " + collectivityId));

        List<Member> members = memberRepository.findAllByCollectivityId(collectivityId);
        List<MembershipFee> activeFees =
                membershipFeeRepository.findActiveByCollectivityId(collectivityId);
        List<Activity> activitiesInPeriod =
                activityRepository.findByCollectivityAndPeriod(collectivityId, from, to);

        List<MemberStatistic> stats = new ArrayList<>();
        for (Member member : members) {
            MemberStatistic stat = new MemberStatistic();
            stat.setMemberId(member.getId());
            stat.setFirstName(member.getFirstName());
            stat.setLastName(member.getLastName());

            // Total collected in period
            long collected = memberPaymentRepository.sumByMemberIdAndPeriod(
                    member.getId(), from, to);
            stat.setTotalCollected(collected);

            // Unpaid for active fees
            long totalOwed = 0L;
            long totalPaidForActiveFees = 0L;
            for (MembershipFee fee : activeFees) {
                totalOwed += fee.getAmount();
                totalPaidForActiveFees += memberPaymentRepository
                        .sumByMemberIdAndFeeIdAndPeriod(member.getId(), fee.getId(), from, to);
            }
            stat.setTotalUnpaid(Math.max(0L, totalOwed - totalPaidForActiveFees));

            // Bonus 2: attendance rate
            if (!activitiesInPeriod.isEmpty()) {
                long mandatory = attendanceRepository.countMandatoryActivitiesForMember(
                        member.getId(), collectivityId, from, to);
                if (mandatory > 0) {
                    long present = attendanceRepository.countPresentForMember(
                            member.getId(), collectivityId, from, to);
                    stat.setAttendanceRate((present * 100.0) / mandatory);
                } else {
                    stat.setAttendanceRate(100.0); // no mandatory activities = 100%
                }
            }
            // if no activities at all, attendanceRate stays null

            stats.add(stat);
        }
        return stats;
    }

    /**
     * GET /collectivities/statistics?from=DATE&to=DATE
     *
     * Per collectivity:
     *  - percentageMembersUpToDate: % members who paid all active dues
     *  - newMembersCount: members whose adhesion_date is in [from, to]
     *  - globalAttendanceRate: average attendance rate across all members (Bonus 2)
     */
    public List<CollectivityStatistic> getFederationStatistics(LocalDate from, LocalDate to) {
        List<CollectivityStatistic> result = new ArrayList<>();

        List<String> collectivityIds = collectivityRepository.findAllIds();
        for (String collectivityId : collectivityIds) {
            Collectivity collectivity = collectivityRepository.findById(collectivityId).orElse(null);
            if (collectivity == null) continue;

            List<Member> members = memberRepository.findAllByCollectivityId(collectivityId);
            List<MembershipFee> activeFees =
                    membershipFeeRepository.findActiveByCollectivityId(collectivityId);
            List<Activity> activitiesInPeriod =
                    activityRepository.findByCollectivityAndPeriod(collectivityId, from, to);

            long upToDateCount = 0L;
            long newMembersCount = 0L;
            double totalAttendanceRate = 0.0;
            int attendanceContributors = 0;

            for (Member member : members) {
                // New members count
                if (member.getMembershipDate() != null
                        && !member.getMembershipDate().isBefore(from)
                        && !member.getMembershipDate().isAfter(to)) {
                    newMembersCount++;
                }

                // Up to date check
                boolean upToDate = true;
                for (MembershipFee fee : activeFees) {
                    long paid = memberPaymentRepository.sumByMemberIdAndFeeIdAndPeriod(
                            member.getId(), fee.getId(), from, to);
                    if (paid < fee.getAmount()) {
                        upToDate = false;
                        break;
                    }
                }
                if (upToDate) upToDateCount++;

                // Bonus 2: accumulate attendance rates
                if (!activitiesInPeriod.isEmpty()) {
                    long mandatory = attendanceRepository.countMandatoryActivitiesForMember(
                            member.getId(), collectivityId, from, to);
                    if (mandatory > 0) {
                        long present = attendanceRepository.countPresentForMember(
                                member.getId(), collectivityId, from, to);
                        totalAttendanceRate += (present * 100.0) / mandatory;
                        attendanceContributors++;
                    }
                }
            }

            double percentage = members.isEmpty() ? 100.0
                    : (upToDateCount * 100.0 / members.size());

            CollectivityStatistic stat = new CollectivityStatistic();
            stat.setCollectivityId(collectivityId);
            stat.setCollectivityName(collectivity.getName());
            stat.setPercentageMembersUpToDate(percentage);
            stat.setNewMembersCount(newMembersCount);

            // Bonus 2: global attendance rate
            if (attendanceContributors > 0) {
                stat.setGlobalAttendanceRate(totalAttendanceRate / attendanceContributors);
            }

            result.add(stat);
        }
        return result;
    }
}
