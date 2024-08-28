package com.todoapp.mapper;

import com.todoapp.dto.TaskRequest;
import com.todoapp.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    Task toEntity(TaskRequest taskDTO);
}