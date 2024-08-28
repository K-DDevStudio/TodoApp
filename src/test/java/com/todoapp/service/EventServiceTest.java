package com.todoapp.service;

import com.todoapp.entity.Event;
import com.todoapp.TestDataForEvents;
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

        Event existingEvent  = mockEvents.getFirst();
        Event updatedEvent = new Event();

        updatedEvent.setId(existingEvent.getId());
        updatedEvent.setTitle(existingEvent.getTitle());
        updatedEvent.setDescription(existingEvent.getDescription());
        updatedEvent.setStartDateTime(existingEvent.getStartDateTime());
        updatedEvent.setEndDateTime(existingEvent.getEndDateTime());

        updatedEvent.setTitle("Updated Title");
        updatedEvent.setDescription("Updated Description");

        when(eventRepository.findById(existingEvent.getId())).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(updatedEvent)).thenReturn(updatedEvent);

        Event result = eventService.updateEvent(updatedEvent, 1L);
        assertEquals(updatedEvent, result);

    }

    @Test
    public void testDeleteEvent() {
        eventService.deleteEvent(1L);

        verify(eventRepository, times(1)).deleteById(1L);
    }

}
