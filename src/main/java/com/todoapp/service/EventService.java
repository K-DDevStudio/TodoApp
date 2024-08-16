package com.todoapp.service;

import com.todoapp.entities.Event;
import com.todoapp.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class EventService {

    private final EventRepository eventRepository;

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event could not be found"));
    }

    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Event event, long id) {
        Event updateEvent = eventRepository.findById(id).
                orElseThrow(() -> new NotFoundException("Id: " + id + " not exist"));

        updateEvent.setTitle(event.getTitle());
        updateEvent.setDescription(event.getDescription());
        updateEvent.setStartDateTime(event.getStartDateTime());
        updateEvent.setEndDateTime(event.getEndDateTime());

        return eventRepository.save(updateEvent);
    }

    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }
}
