package com.fsd.event.service;

import com.fsd.event.entity.EventManager;
import com.fsd.event.repository.EventManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventManagerService {

    private final EventManagerRepository managerRepository;

    public EventManager createManager(EventManager manager) {
        return managerRepository.save(manager);
    }

    public List<EventManager> getAllManagers() {
        return managerRepository.findAll();
    }

    public Optional<EventManager> getManagerById(Integer id) {
        return managerRepository.findById(id);
    }
}
