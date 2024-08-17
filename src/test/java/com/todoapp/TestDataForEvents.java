package com.todoapp;

import com.todoapp.entities.Event;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class TestDataForEvents {

    public static List<Event> getMockEvents() {

        Event event1 = new Event();
        event1.setId(1L);
        event1.setTitle("First Event");
        event1.setDescription("Description for the first event");
        event1.setStartDateTime(LocalDateTime.now().plusDays(1));
        event1.setEndDateTime(LocalDateTime.now().plusDays(2));

        Event event2 = new Event();
        event2.setId(2L);
        event2.setTitle("Second Event");
        event2.setDescription("Description for the second event");
        event2.setStartDateTime(LocalDateTime.now().plusDays(2));
        event2.setEndDateTime(LocalDateTime.now().plusDays(3));

        return Arrays.asList(event1, event2);
    }

}
