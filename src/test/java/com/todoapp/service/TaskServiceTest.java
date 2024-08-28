package com.todoapp.service;

import com.todoapp.dto.TaskRequest;
import com.todoapp.entity.Task;
import com.todoapp.exception.TaskNotFoundException;
import com.todoapp.mapper.TaskMapper;
import com.todoapp.repository.TaskRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static com.todoapp.test_utils.TestTaskDataFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private Validator validator;

    @InjectMocks
    private TaskService taskService;

    @Nested
    class GetAllTasksTests {

        @Test
        void shouldReturnEmptyListWhenNoTasksFound() {
            when(taskRepository.findAll()).thenReturn(Collections.emptyList());

            final var tasks = taskService.getAllTasks();

            assertThat(tasks).isEmpty();
            verify(taskRepository, times(1)).findAll();
        }

        @Test
        void shouldReturnListOfTasksWhenTasksExist() {
            final var mockTasks = createDefaultTaskList(2);
            when(taskRepository.findAll()).thenReturn(mockTasks);

            final var tasks = taskService.getAllTasks();

            assertThat(tasks)
                    .hasSize(2)
                    .isEqualTo(mockTasks);
            verify(taskRepository, times(1)).findAll();
        }
    }

    @Nested
    class GetTaskByIdTests {

        @Test
        void shouldReturnTaskWhenFoundById() {
            final var task = createDefaultTask();
            when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

            final var foundTask = taskService.getTaskById(1L);

            assertThat(foundTask)
                    .isNotNull()
                    .isEqualTo(task);
            verify(taskRepository, times(1)).findById(1L);
        }

        @Test
        void shouldThrowNotFoundExceptionWhenTaskDoesNotExist() {
            when(taskRepository.findById(1L)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> taskService.getTaskById(1L))
                    .isInstanceOf(TaskNotFoundException.class)
                    .hasMessageContaining("Task not found with id 1");
            verify(taskRepository, times(1)).findById(1L);
        }
    }

    @Nested
    class CreateTaskTests {

        @Test
        void shouldCreateTaskWhenValidRequest() {
            final var taskRequest = createDefaultTaskRequest();
            final var task = createDefaultTask();
            when(validator.validate(taskRequest)).thenReturn(Collections.emptySet());
            when(taskMapper.toEntity(taskRequest)).thenReturn(task);
            when(taskRepository.save(any(Task.class))).thenReturn(task);

            final var createdTask = taskService.createTask(taskRequest);

            assertThat(createdTask).isNotNull().isEqualTo(task);
            verify(validator, times(1)).validate(taskRequest);
            verify(taskMapper, times(1)).toEntity(taskRequest);
            verify(taskRepository, times(1)).save(any(Task.class));
        }

        @Test
        void shouldThrowValidationExceptionWhenRequestIsInvalid() {
            final var taskRequest = createDefaultTaskRequest();
            final ConstraintViolation<TaskRequest> violation = mock(ConstraintViolation.class);
            when(violation.getMessage()).thenReturn("Invalid task request");
            when(validator.validate(taskRequest)).thenReturn(Set.of(violation));

            ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> taskService.createTask(taskRequest));
            assertThat(thrown.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
            assertThat(thrown.getReason()).contains("Invalid task request");

            verify(validator, times(1)).validate(taskRequest);
            verify(taskMapper, never()).toEntity(any());
            verify(taskRepository, never()).save(any());
        }
    }

    @Nested
    class UpdateTaskTests {

        @Test
        void shouldUpdateAndReturnTaskWhenTaskExists() {
            final var taskRequest = createDefaultTaskRequest();
            final var task = createDefaultTask();
            when(taskRepository.existsById(1L)).thenReturn(true);
            when(validator.validate(taskRequest)).thenReturn(Collections.emptySet());
            when(taskMapper.toEntity(taskRequest)).thenReturn(task);
            when(taskRepository.save(any(Task.class))).thenReturn(task);

            final var updatedTask = taskService.updateTask(1L, taskRequest);

            assertThat(updatedTask).isNotNull().isEqualTo(task);
            verify(taskRepository, times(1)).existsById(1L);
            verify(validator, times(1)).validate(taskRequest);
            verify(taskMapper, times(1)).toEntity(taskRequest);
            verify(taskRepository, times(1)).save(any(Task.class));
        }

        @Test
        void shouldThrowResponseStatusExceptionWhenTaskToUpdateDoesNotExist() {
            final var taskRequest = createDefaultTaskRequest();
            when(taskRepository.existsById(1L)).thenReturn(false);

            assertThatThrownBy(() -> taskService.updateTask(1L, taskRequest))
                    .isInstanceOf(TaskNotFoundException.class)
                    .hasMessageContaining("Task not found with id 1");
            verify(taskRepository, times(1)).existsById(1L);
            verify(validator, never()).validate(any());
            verify(taskMapper, never()).toEntity(any());
            verify(taskRepository, never()).save(any());
        }
    }

    @Nested
    class DeleteTaskTests {

        @Test
        void shouldDeleteTaskWhenTaskExists() {
            final var taskId = 1L;
            when(taskRepository.existsById(any())).thenReturn(true);

            taskService.deleteTask(taskId);

            verify(taskRepository, times(1)).deleteById(taskId);
        }
    }
}