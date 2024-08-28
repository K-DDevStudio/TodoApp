package com.todoapp.controller;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.todoapp.dto.TaskRequest;
import com.todoapp.entity.Task;
import com.todoapp.entity.enums.Priority;
import com.todoapp.entity.enums.Status;
import com.todoapp.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.todoapp.test_utils.TestTaskDataFactory.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @Nested
    class WhenTasksExist {

        @Test
        void shouldReturnTasks() throws Exception {
            final var tasks = createDefaultTaskList(1);
            when(taskService.getAllTasks()).thenReturn(tasks);

            mockMvc.perform(get("/api/tasks"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$[0].title").value("Task1"));
        }

        @Test
        void shouldReturnTask() throws Exception {
            final Task task = createDefaultTask();
            when(taskService.getTaskById(1L)).thenReturn(task);

            mockMvc.perform(get("/api/tasks/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.title").value("Default Task Name"));
        }
    }

    @Nested
    class WhenCreatingOrUpdatingTask {

        @Test
        void shouldCreateTask() throws Exception {
            final TaskRequest taskRequest = createDefaultTaskRequest();
            final Task task = createDefaultTask();
            when(taskService.createTask(taskRequest)).thenReturn(task);

            mockMvc.perform(post("/api/tasks")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(taskRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.title").value("Default Task Name"));
        }

        @Test
        void shouldUpdateTask() throws Exception {
            final TaskRequest taskRequest = createDefaultTaskRequest();
            final Task updatedTask = createTask(1L, "Updated Title", "Updated Description", Priority.MEDIUM, LocalDate.now(), Status.COMPLETED);
            when(taskService.updateTask(1L, taskRequest)).thenReturn(updatedTask);

            mockMvc.perform(put("/api/tasks/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(taskRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.title").value("Updated Title"));
        }
    }

    @Test
    void shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());

        verify(taskService).deleteTask(1L);
    }
}