package service_impl;

import entities.Event;
import repository.EventRepository;
import service.EventService;

import java.util.List;
import java.util.Optional;

public class EventServiceImpl implements EventService {

    private EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Optional<Event> getEventById(long id) {
        return eventRepository.findById(id);
    }

    @Override
    public Event updateEvent(Event event, long id) {
        Event updateEvent = eventRepository.findById(id).
                orElseThrow(() -> new RuntimeException());

        updateEvent.setTitle(event.getTitle());
        updateEvent.setDescription(event.getDescription());
        updateEvent.setStartDateTime(event.getStartDateTime());
        updateEvent.setEndDateTime(event.getEndDateTime());

        return eventRepository.save(updateEvent);
    }

    @Override
    public void deleteEvent(long id) {
        eventRepository.deleteById(id);
    }
}
