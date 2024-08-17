package com.todoapp.mapper;

import com.todoapp.dto.TaskRequest;
import com.todoapp.entity.task.enums.Priority;
import com.todoapp.entity.task.enums.Status;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class TaskMapperTest {

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    @Nested
    class WhenMapping {

        @Test
        void shouldMapValidTaskRequestToTask() {
            final var taskRequest = new TaskRequest(
                    "Title",
                    "Description",
                    Priority.HIGH,
                    LocalDate.now(),
                    Status.PENDING
            );

            final var task = taskMapper.toEntity(taskRequest);

            assertThat(task).isNotNull();
            assertThat(task.getTitle()).isEqualTo("Title");
            assertThat(task.getDescription()).isEqualTo("Description");
            assertThat(task.getPriority()).isEqualTo(Priority.HIGH);
            assertThat(task.getDeadline()).isEqualTo(taskRequest.deadline());
            assertThat(task.getStatus()).isEqualTo(Status.PENDING);
        }

        @Test
        void shouldReturnNullWhenTaskRequestIsNull() {
            final var task = taskMapper.toEntity(null);

            assertThat(task).isNull();
        }
    }
}