package com.todoapp.service;

import com.todoapp.TestDataForEvents;
import com.todoapp.entities.Event;
import com.todoapp.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Test
    public void testGetAllEvents() {

        List<Event> mockEvents = TestDataForEvents.getMockEvents();

        when(eventRepository.findAll()).thenReturn(mockEvents);

        assertEquals(mockEvents, eventService.getAllEvents());

    }

    @Test
    public void testGetEventById() {

        List<Event> mockEvents = TestDataForEvents.getMockEvents();

        Event eventWithId1L = mockEvents.stream()
                .filter(event -> event.getId().equals(1L))
                .findFirst()
                .orElse(null);

        when(eventRepository.findById(1L)).thenReturn(Optional.ofNullable(eventWithId1L));

        assertEquals(eventWithId1L, eventService.getEventById(1L));

    }
    @Test
    public void testSaveEvent() {

        List<Event> mockEvents = TestDataForEvents.getMockEvents();

        Event newEvent = mockEvents.get(1);

        when(eventRepository.save(newEvent)).thenReturn(newEvent);

        Event savedEvent = eventService.saveEvent(newEvent);

        assertEquals(newEvent, savedEvent);

    }

    @Test
    public void testUpdateEvent() {

        List<Event> mockEvents = TestDataForEvents.getMockEvents();

        Event existingEvent  = mockEvents.get(1);
        Event updatedEvent = new Event();

        updatedEvent.setId(existingEvent.getId());
        updatedEvent.setTitle(existingEvent.getTitle());
        updatedEvent.setDescription(existingEvent.getDescription());
        updatedEvent.setStartDateTime(existingEvent.getStartDateTime());
        updatedEvent.setEndDateTime(existingEvent.getEndDateTime());

        when(eventRepository.findById(existingEvent.getId())).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(existingEvent)).thenReturn(existingEvent);

        existingEvent.setTitle("Updated Title");
        existingEvent.setDescription("Updated Description");

        eventService.updateEvent(existingEvent, existingEvent.getId());

        //??
        // assertEquals(existingEvent, updatedEvent);

    }

    @Test
    public void testDeleteEvent() {

        List<Event> mockEvents = TestDataForEvents.getMockEvents();

        Event deleteEvent = mockEvents.get(1);

        // Mock the repository's findById method to return the event to be deleted
        when(eventRepository.findById(deleteEvent.getId())).thenReturn(Optional.of(deleteEvent));

    }

}
