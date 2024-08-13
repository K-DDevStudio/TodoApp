package service;

import entities.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {

    Event saveEvent(Event event);
    List<Event> getAllEvents();
    Optional<Event> getEventById(long id);
    Event updateEvent(Event event, long id);
    void deleteEvent(long id);

}
