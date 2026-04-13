package com.example.auth_service.mapper;

import com.example.auth_service.dto.UserDTO;
import com.example.auth_service.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User user);
}
