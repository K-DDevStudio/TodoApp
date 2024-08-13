package controller;

import org.springframework.web.bind.annotation.*;
import service.EventService;
import entities.Event;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/events")
public class EventController {

    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    //Sets the HTTP response status to 201 Created if the method completes successfully.
    // This is a common response code for successful POST requests that result in a resource being created.
    public Event createEvent(@RequestBody Event event){
        return eventService.saveEvent(event);
    }

    @GetMapping("/events/{id}")
    public Optional<Event> getById(@RequestBody Long id){
        return eventService.getEventById(id);
    }

    @GetMapping("/events")
    public List<Event> getAll(){
        return eventService.getAllEvents();
    }

    @DeleteMapping("/events/{id}")
    public void deleteEvent(@PathVariable long id){
        eventService.deleteEvent(id);
    }

}
