package com.nexus.auth.model.mapper;

import com.nexus.auth.model.User;
import com.nexus.auth.model.dto.UserRequestDTO;
import com.nexus.auth.model.dto.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toDTO(User user);

    User toModel(UserRequestDTO userRequestDTO);
}
