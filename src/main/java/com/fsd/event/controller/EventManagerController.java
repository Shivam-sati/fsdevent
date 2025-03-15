package com.fsd.event.controller;

import com.fsd.event.entity.EventManager;
import com.fsd.event.service.EventManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/managers")
@RequiredArgsConstructor
public class EventManagerController {

    private final EventManagerService managerService;

    @PostMapping
    public EventManager createManager(@RequestBody EventManager manager) {
        return managerService.createManager(manager);
    }

    @GetMapping
    public List<EventManager> getAllManagers() {
        return managerService.getAllManagers();
    }

    @GetMapping("/{id}")
    public EventManager getManagerById(@PathVariable Integer id) {
        return managerService.getManagerById(id).orElse(null);
    }
}

