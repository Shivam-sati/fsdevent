package com.fsd.event.service;

import com.fsd.event.entity.Event;
import com.fsd.event.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Optional<Event> getEventById(Integer id) {
        return eventRepository.findById(id);
    }

    // Get all participants across all activities for an event
    public List<?> getAllParticipantsInEvent(Integer eventId) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if (eventOpt.isPresent()) {
            return eventOpt.get().getActivities()
                    .stream()
                    .flatMap(activity -> activity.getParticipants().stream())
                    .distinct()
                    .toList();
        }
        return List.of();
    }
}
