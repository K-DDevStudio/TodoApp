package com.todoapp.mapper;

import com.todoapp.entity.enums.Priority;
import com.todoapp.entity.enums.Status;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static com.todoapp.test_utils.TestTaskDataFactory.createDefaultTaskRequest;
import static org.assertj.core.api.Assertions.assertThat;

class TaskMapperTest {

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    @Nested
    class WhenMapping {

        @Test
        void shouldMapValidTaskRequestToTask() {
            final var taskRequest = createDefaultTaskRequest();

            final var task = taskMapper.toEntity(taskRequest);

            assertThat(task).isNotNull();
            assertThat(task.getTitle()).isEqualTo("Default Task Name");
            assertThat(task.getDescription()).isEqualTo("Default Description");
            assertThat(task.getPriority()).isEqualTo(Priority.MEDIUM);
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