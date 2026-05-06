package hei.school.agriculturalFederation.service;

import hei.school.agriculturalFederation.exception.BadRequestException;
import hei.school.agriculturalFederation.exception.NotFoundException;
import hei.school.agriculturalFederation.model.CreateMembershipFee;
import hei.school.agriculturalFederation.model.MembershipFee;
import hei.school.agriculturalFederation.repository.CollectivityRepository;
import hei.school.agriculturalFederation.repository.MembershipFeeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MembershipFeeService {

    private final MembershipFeeRepository membershipFeeRepository;
    private final CollectivityRepository collectivityRepository;

    public MembershipFeeService(MembershipFeeRepository membershipFeeRepository,
                                CollectivityRepository collectivityRepository) {
        this.membershipFeeRepository = membershipFeeRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<MembershipFee> createFees(String collectivityId, List<CreateMembershipFee> requests) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new NotFoundException("Collectivity not found: " + collectivityId);
        }
        List<MembershipFee> result = new ArrayList<>();
        for (CreateMembershipFee req : requests) {
            if (req.getLabel() == null || req.getLabel().isBlank()) {
                throw new BadRequestException("Membership fee label is required.");
            }
            if (req.getAmount() <= 0) {
                throw new BadRequestException("Membership fee amount must be positive.");
            }
            if (req.getFeeType() == null) {
                throw new BadRequestException("Membership fee type is required.");
            }
            MembershipFee fee = new MembershipFee();
            fee.setId(UUID.randomUUID().toString());
            fee.setCollectivityId(collectivityId);
            fee.setLabel(req.getLabel());
            fee.setAmount(req.getAmount());
            fee.setFeeType(req.getFeeType());
            fee.setActive(true);
            fee.setCreatedAt(LocalDate.now());
            result.add(membershipFeeRepository.save(fee));
        }
        return result;
    }

    public List<MembershipFee> getFees(String collectivityId) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new NotFoundException("Collectivity not found: " + collectivityId);
        }
        return membershipFeeRepository.findAllByCollectivityId(collectivityId);
    }
}
