package hei.school.agriculturalFederation.service;

import hei.school.agriculturalFederation.exception.BadRequestException;
import hei.school.agriculturalFederation.exception.NotFoundException;
import hei.school.agriculturalFederation.model.*;
import hei.school.agriculturalFederation.repository.ActivityRepository;
import hei.school.agriculturalFederation.repository.CollectivityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final CollectivityRepository collectivityRepository;

    public ActivityService(ActivityRepository activityRepository,
                           CollectivityRepository collectivityRepository) {
        this.activityRepository = activityRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<Activity> createActivities(String collectivityId, List<CreateActivity> requests) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new NotFoundException("Collectivity not found: " + collectivityId);
        }
        List<Activity> result = new ArrayList<>();
        for (CreateActivity req : requests) {
            if (req.getTitle() == null || req.getTitle().isBlank()) {
                throw new BadRequestException("Activity title is required.");
            }
            if (req.getActivityDate() == null) {
                throw new BadRequestException("Activity date is required.");
            }
            if (req.getActivityType() == null) {
                throw new BadRequestException("Activity type is required.");
            }
            Activity activity = new Activity();
            activity.setId(UUID.randomUUID().toString());
            activity.setCollectivityId(collectivityId);
            activity.setTitle(req.getTitle());
            activity.setActivityDate(req.getActivityDate());
            activity.setActivityType(req.getActivityType());
            activity.setAttendanceRequirement(
                    req.getAttendanceRequirement() != null
                            ? req.getAttendanceRequirement()
                            : AttendanceRequirement.ALL);
            result.add(activityRepository.save(activity));
        }
        return result;
    }

    public List<Activity> getActivities(String collectivityId) {
        if (!collectivityRepository.existsById(collectivityId)) {
            throw new NotFoundException("Collectivity not found: " + collectivityId);
        }
        return activityRepository.findAllByCollectivityId(collectivityId);
    }
}
