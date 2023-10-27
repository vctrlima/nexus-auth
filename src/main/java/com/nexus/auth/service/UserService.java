package com.nexus.auth.service;

import com.nexus.auth.model.dto.AuthRequestDTO;
import com.nexus.auth.model.dto.UserRequestDTO;
import com.nexus.auth.model.dto.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public interface UserService {
    public UserResponseDTO create(UserRequestDTO user);

    public Page<UserResponseDTO> findAll(Pageable pageable);

    public Optional<UserResponseDTO> findById(UUID id);

    public UserResponseDTO update(UUID id, UserRequestDTO user);

    public void delete(UUID id);

    public UserResponseDTO login(AuthRequestDTO credentials) throws Exception;
}
