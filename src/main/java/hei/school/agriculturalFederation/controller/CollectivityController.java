package hei.school.agriculturalFederation.controller;

import hei.school.agriculturalFederation.model.*;
import hei.school.agriculturalFederation.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/collectivities")
public class CollectivityController {

    private final CollectivityService collectivityService;
    private final CollectivityInformationService collectivityInformationService;
    private final MembershipFeeService membershipFeeService;
    private final FinancialAccountService financialAccountService;
    private final FinancialTransactionService financialTransactionService;
    private final ActivityService activityService;
    private final AttendanceService attendanceService;
    private final StatisticsService statisticsService;

    public CollectivityController(
            CollectivityService collectivityService,
            CollectivityInformationService collectivityInformationService,
            MembershipFeeService membershipFeeService,
            FinancialAccountService financialAccountService,
            FinancialTransactionService financialTransactionService,
            ActivityService activityService,
            AttendanceService attendanceService,
            StatisticsService statisticsService) {
        this.collectivityService = collectivityService;
        this.collectivityInformationService = collectivityInformationService;
        this.membershipFeeService = membershipFeeService;
        this.financialAccountService = financialAccountService;
        this.financialTransactionService = financialTransactionService;
        this.activityService = activityService;
        this.attendanceService = attendanceService;
        this.statisticsService = statisticsService;
    }

    // ─── A: Collectivities ──────────────────────────────────────────────────

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Collectivity> createCollectivities(
            @RequestBody List<CreateCollectivity> collectivities) {
        return collectivityService.createCollectivities(collectivities);
    }

    @GetMapping("/{id}")
    public Collectivity getCollectivityById(@PathVariable String id) {
        return collectivityService.getById(id);
    }

    // ─── J: Assign unique number and name ───────────────────────────────────

    @PutMapping("/{id}/informations")
    public Collectivity assignInformation(
            @PathVariable String id,
            @RequestBody CollectivityInformation info) {
        return collectivityInformationService.assignInformation(id, info);
    }

    // ─── C: Membership fees ─────────────────────────────────────────────────

    @PostMapping("/{id}/membershipFees")
    @ResponseStatus(HttpStatus.CREATED)
    public List<MembershipFee> createMembershipFees(
            @PathVariable String id,
            @RequestBody List<CreateMembershipFee> fees) {
        return membershipFeeService.createFees(id, fees);
    }

    @GetMapping("/{id}/membershipFees")
    public List<MembershipFee> getMembershipFees(@PathVariable String id) {
        return membershipFeeService.getFees(id);
    }

    // ─── D: Financial accounts ──────────────────────────────────────────────

    @PostMapping("/{id}/financialAccounts")
    @ResponseStatus(HttpStatus.CREATED)
    public List<FinancialAccount> createFinancialAccounts(
            @PathVariable String id,
            @RequestBody List<CreateFinancialAccount> accounts) {
        return financialAccountService.createAccounts(id, accounts);
    }

    @GetMapping("/{id}/financialAccounts")
    public List<FinancialAccount> getFinancialAccounts(
            @PathVariable String id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate at) {
        return financialAccountService.getAccountsWithBalanceAt(id, at);
    }

    // ─── D: Transactions ────────────────────────────────────────────────────

    @GetMapping("/{id}/transactions")
    public List<FinancialTransaction> getTransactions(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return financialTransactionService.getTransactions(id, from, to);
    }

    // ─── E: Activities (Bonus) ──────────────────────────────────────────────

    @PostMapping("/{id}/activities")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Activity> createActivities(
            @PathVariable String id,
            @RequestBody List<CreateActivity> activities) {
        return activityService.createActivities(id, activities);
    }

    @GetMapping("/{id}/activities")
    public List<Activity> getActivities(@PathVariable String id) {
        return activityService.getActivities(id);
    }

    // ─── F: Attendance (Bonus) ──────────────────────────────────────────────

    @PostMapping("/{id}/activities/{activityId}/attendance")
    @ResponseStatus(HttpStatus.CREATED)
    public List<AttendanceRecord> recordAttendance(
            @PathVariable String id,
            @PathVariable String activityId,
            @RequestBody List<AttendanceRecord> records) {
        return attendanceService.recordAttendance(id, activityId, records);
    }

    @GetMapping("/{id}/activities/{activityId}/attendance")
    public List<AttendanceRecord> getAttendance(
            @PathVariable String id,
            @PathVariable String activityId) {
        return attendanceService.getAttendance(id, activityId);
    }

    // ─── G: Collectivity statistics (+ Bonus 2) ─────────────────────────────

    @GetMapping("/{id}/statistics")
    public List<MemberStatistic> getCollectivityStatistics(
            @PathVariable String id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return statisticsService.getCollectivityStatistics(id, from, to);
    }

    // ─── H: Federation statistics (+ Bonus 2) ───────────────────────────────

    @GetMapping("/statistics")
    public List<CollectivityStatistic> getFederationStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return statisticsService.getFederationStatistics(from, to);
    }
}
