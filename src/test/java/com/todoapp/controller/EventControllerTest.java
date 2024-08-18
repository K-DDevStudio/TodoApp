package com.todoapp.controller;

import com.todoapp.TestDataForEvents;
import com.todoapp.entities.Event;
import com.todoapp.repository.EventRepository;
import com.todoapp.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    @Test
    public void testGetAllEvents() {
        List<Event> mockEvents = TestDataForEvents.getMockEvents();

        when(eventService.getAllEvents()).thenReturn(mockEvents);

        ResponseEntity<List<Event>> response = eventController.getAll();

        assertEquals(ResponseEntity.ok(mockEvents), response);

    }

    @Test
    public void testGetEventById() {
        List<Event> mockEvents = TestDataForEvents.getMockEvents();

        Event eventWithId1L = mockEvents.stream()
                .filter(event -> event.getId().equals(1L))
                .findFirst()
                .orElse(null);

        when(eventService.getEventById(1L)).thenReturn(eventWithId1L);

        ResponseEntity<Event> response = eventController.getById(1L);

        assertEquals(ResponseEntity.ok(eventWithId1L), response);

    }

    @Test
    public void testDeleteEvent() {

        ResponseEntity<Void> response = eventController.deleteEvent(1L);

        assertEquals(ResponseEntity.noContent().build(), response);

    }

}
