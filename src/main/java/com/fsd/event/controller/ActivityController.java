package com.fsd.event.controller;

import com.fsd.event.entity.Activity;
import com.fsd.event.entity.Participant;
import com.fsd.event.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    // Create an activity under a specific event
    @PostMapping("/event/{eventId}")
    public Activity createActivity(@PathVariable Integer eventId, @RequestBody Activity activity) {
        return activityService.createActivity(eventId, activity);
    }

    // List all activities
    @GetMapping
    public List<Activity> getAllActivities() {
        return activityService.getAllActivities();
    }

    @GetMapping("/{id}")
    public Activity getActivityById(@PathVariable Integer id) {
        return activityService.getActivityById(id).orElse(null);
    }

    /**
     * Endpoint to register an existing participant to an activity.
     * If the participant exists and details match, they'll be registered.
     */
    @PostMapping("/{id}/register")
    public Activity addParticipantToActivity(@PathVariable Integer id, @RequestBody Participant participant) {
        return activityService.addParticipantToActivity(id, participant);
    }

    // List participants for a specific activity
    @GetMapping("/{id}/participants")
    public List<Participant> getParticipantsForActivity(@PathVariable Integer id) {
        return activityService.getParticipantsForActivity(id);
    }
}
