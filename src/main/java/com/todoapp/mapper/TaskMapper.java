package com.todoapp.mapper;

import com.todoapp.dto.TaskRequest;
import com.todoapp.entity.task.Task;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toEntity(TaskRequest taskDTO);
}