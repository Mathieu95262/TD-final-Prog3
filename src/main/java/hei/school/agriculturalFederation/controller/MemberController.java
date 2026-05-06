package hei.school.agriculturalFederation.controller;

import hei.school.agriculturalFederation.model.CreateMember;
import hei.school.agriculturalFederation.model.CreateMemberPayment;
import hei.school.agriculturalFederation.model.Member;
import hei.school.agriculturalFederation.model.MemberPayment;
import hei.school.agriculturalFederation.service.MemberPaymentService;
import hei.school.agriculturalFederation.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberPaymentService memberPaymentService;

    public MemberController(MemberService memberService,
                            MemberPaymentService memberPaymentService) {
        this.memberService = memberService;
        this.memberPaymentService = memberPaymentService;
    }

    // B-2 - Admit new members
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Member> createMembers(@RequestBody List<CreateMember> members) {
        return memberService.createMembers(members);
    }

    // C - Record payments for a member
    @PostMapping("/{id}/payments")
    @ResponseStatus(HttpStatus.CREATED)
    public List<MemberPayment> recordPayments(
            @PathVariable String id,
            @RequestBody List<CreateMemberPayment> payments) {
        return memberPaymentService.recordPayments(id, payments);
    }
}
