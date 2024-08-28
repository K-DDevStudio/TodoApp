package com.todoapp.service;

import com.todoapp.controller.EventController;
import com.todoapp.entity.task.Event;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    //@Mock используется для создания имитации объекта EventService.
    @Mock
    private EventService eventService;

    //Аннотация @InjectMocks внедряет все зависимости в объект EventController.
    @InjectMocks
    private EventController eventController;

    @Test
    public void testGetAllEvents() {
        // Arrange
        Event event1 = new Event();

        event1.setId(1L);
        event1.setTitle("First Event");
        event1.setDescription("Description for the first event");
        event1.setStartDateTime(LocalDateTime.now().plusDays(1));
        event1.setEndDateTime(LocalDateTime.now().plusDays(2));

        when(eventService.getAllEvents()).thenReturn(singletonList(event1));

        ResponseEntity<List<Event>> response = eventController.getAll();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(event1, response.getBody().get(0));

    }
}
