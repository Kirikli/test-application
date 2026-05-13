package com.example.search_service.mapper;

import com.example.search_service.dto.TaskDTO;
import com.example.search_service.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDTO toDTO(Task task);
}
