package com.fsd.event.controller;

import com.fsd.event.entity.Activity;
import com.fsd.event.entity.Event;
import com.fsd.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    // Get all events (with their activities)
    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    // Get a specific event by id (including details and activities)
    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Integer id) {
        return eventService.getEventById(id).orElse(null);
    }

    // Get all activities for a specific event
    @GetMapping("/{id}/activities")
    public List<Activity> getActivitiesForEvent(@PathVariable Integer id) {
        return eventService.getEventById(id)
                .map(Event::getActivities)
                .orElse(List.of());
    }

    // Get all participants across all activities for an event
    @GetMapping("/{id}/participants")
    public List<?> getParticipantsInEvent(@PathVariable Integer id) {
        return eventService.getAllParticipantsInEvent(id);
    }
}
