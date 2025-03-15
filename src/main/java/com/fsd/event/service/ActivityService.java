package com.fsd.event.service;

import com.fsd.event.entity.Activity;
import com.fsd.event.entity.Event;
import com.fsd.event.entity.Participant;
import com.fsd.event.repository.ActivityRepository;
import com.fsd.event.repository.EventRepository;
import com.fsd.event.repository.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    public Activity createActivity(Integer eventId, Activity activity) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isPresent()) {
            activity.setEvent(eventOpt.get());
            return activityRepository.save(activity);
        }
        throw new RuntimeException("Event not found with id: " + eventId);
    }

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Optional<Activity> getActivityById(Integer id) {
        return activityRepository.findById(id);
    }

    /**
     * Register an existing participant to an activity.
     * If a participant with the submitted email is not found,
     * throw an error. If found, compare the submitted details (name and phone)
     * with the stored record. If they match, register the participant to the activity.
     */
    @Transactional
    public Activity addParticipantToActivity(Integer activityId, Participant participant) {
        Optional<Activity> actOpt = activityRepository.findById(activityId);
        if (actOpt.isEmpty()) {
            throw new RuntimeException("Activity not found with id: " + activityId);
        }
        // Lookup participant by email
        Optional<Participant> existingOpt = participantRepository.findByEmail(participant.getEmail());
        if (existingOpt.isEmpty()) {
            throw new RuntimeException("Participant not found. Please register first.");
        }
        Participant existingParticipant = existingOpt.get();
        // Compare details (e.g., name and phone)
        if (!existingParticipant.getName().equals(participant.getName()) ||
            !existingParticipant.getPhone().equals(participant.getPhone())) {
            throw new RuntimeException("Participant details do not match the registered information.");
        }
        Activity activity = actOpt.get();
        // If not already registered, add the participant to the activity
        if (!activity.getParticipants().contains(existingParticipant)) {
            activity.getParticipants().add(existingParticipant);
            existingParticipant.getActivities().add(activity); // if bidirectional
            activityRepository.save(activity);
        }
        return activity;
    }

    // Get all participants for a given activity
    public List<Participant> getParticipantsForActivity(Integer activityId) {
        Optional<Activity> actOpt = activityRepository.findById(activityId);
        return actOpt.map(Activity::getParticipants).orElse(List.of());
    }
}
