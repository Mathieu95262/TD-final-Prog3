package hei.school.agriculturalFederation.service;

import hei.school.agriculturalFederation.exception.BadRequestException;
import hei.school.agriculturalFederation.exception.NotFoundException;
import hei.school.agriculturalFederation.model.CreateMemberPayment;
import hei.school.agriculturalFederation.model.MemberPayment;
import hei.school.agriculturalFederation.model.MembershipFee;
import hei.school.agriculturalFederation.repository.MemberPaymentRepository;
import hei.school.agriculturalFederation.repository.MemberRepository;
import hei.school.agriculturalFederation.repository.MembershipFeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MemberPaymentService {

    private final MemberPaymentRepository memberPaymentRepository;
    private final MemberRepository memberRepository;
    private final MembershipFeeRepository membershipFeeRepository;

    public MemberPaymentService(MemberPaymentRepository memberPaymentRepository,
                                MemberRepository memberRepository,
                                MembershipFeeRepository membershipFeeRepository) {
        this.memberPaymentRepository = memberPaymentRepository;
        this.memberRepository = memberRepository;
        this.membershipFeeRepository = membershipFeeRepository;
    }

    public List<MemberPayment> recordPayments(String memberId, List<CreateMemberPayment> requests) {
        memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("Member not found: " + memberId));

        List<MemberPayment> result = new ArrayList<>();
        for (CreateMemberPayment req : requests) {
            if (req.getMembershipFeeId() == null) {
                throw new BadRequestException("membershipFeeId is required.");
            }
            MembershipFee fee = membershipFeeRepository.findById(req.getMembershipFeeId())
                    .orElseThrow(() -> new NotFoundException(
                            "Membership fee not found: " + req.getMembershipFeeId()));

            if (!fee.isActive()) {
                throw new BadRequestException(
                        "Membership fee " + fee.getId() + " is no longer active.");
            }
            if (req.getAmount() <= 0) {
                throw new BadRequestException("Payment amount must be positive.");
            }
            if (req.getPaymentMode() == null) {
                throw new BadRequestException("Payment mode is required.");
            }

            MemberPayment payment = new MemberPayment();
            payment.setId(UUID.randomUUID().toString());
            payment.setMemberId(memberId);
            payment.setMembershipFeeId(req.getMembershipFeeId());
            payment.setAmount(req.getAmount());
            payment.setPaymentDate(req.getPaymentDate() != null ? req.getPaymentDate() : LocalDate.now());
            payment.setPaymentMode(req.getPaymentMode());
            result.add(memberPaymentRepository.save(payment));
        }
        return result;
    }
}
