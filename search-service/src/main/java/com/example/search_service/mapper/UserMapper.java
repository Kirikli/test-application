package com.example.search_service.mapper;

import com.example.search_service.dto.UserDTO;
import com.example.search_service.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
}
