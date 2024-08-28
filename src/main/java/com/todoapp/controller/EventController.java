package com.todoapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.todoapp.service.EventService;
import com.todoapp.entity.Event;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("")
    public ResponseEntity<List<Event>> getAll(){
        final var events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getById(@RequestBody final Long id){
        final var event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable long id){
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("")
//    @ResponseStatus(HttpStatus.CREATED)
//    //Sets the HTTP response status to 201 Created if the method completes successfully.
//    //This is a common response code for successful POST requests that result in a resource being created.
    public ResponseEntity<Event> createEvent(@RequestBody Event event){
        final var newEvent = eventService.saveEvent(event);
        return ResponseEntity.ok(newEvent);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event, long id){
        final var updateEvent = eventService.updateEvent(event, id);
        return ResponseEntity.ok(updateEvent);
    }


}
