package hei.school.agriculturalFederation.service;

import hei.school.agriculturalFederation.exception.BadRequestException;
import hei.school.agriculturalFederation.exception.NotFoundException;
import hei.school.agriculturalFederation.model.Collectivity;
import hei.school.agriculturalFederation.model.CollectivityInformation;
import hei.school.agriculturalFederation.repository.CollectivityRepository;
import org.springframework.stereotype.Service;

@Service
public class CollectivityInformationService {

    private final CollectivityRepository collectivityRepository;

    public CollectivityInformationService(CollectivityRepository collectivityRepository) {
        this.collectivityRepository = collectivityRepository;
    }

    public Collectivity assignInformation(String id, CollectivityInformation info) {
        Collectivity collectivity = collectivityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Collectivity not found: " + id));

        // Once a number is assigned it cannot be changed
        if (collectivity.getUniqueNumber() != null && info.getUniqueNumber() != null
                && !info.getUniqueNumber().equals(collectivity.getUniqueNumber())) {
            throw new BadRequestException(
                    "Unique number has already been assigned and cannot be changed.");
        }

        // Once a name is assigned it cannot be changed
        if (collectivity.getUniqueName() != null && info.getUniqueName() != null
                && !info.getUniqueName().equals(collectivity.getUniqueName())) {
            throw new BadRequestException(
                    "Unique name has already been assigned and cannot be changed.");
        }

        collectivityRepository.updateInformation(id, info.getUniqueNumber(), info.getUniqueName());

        return collectivityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Collectivity not found after update: " + id));
    }
}
