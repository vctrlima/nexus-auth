package com.nexus.auth.service.impl;

import com.nexus.auth.model.User;
import com.nexus.auth.model.dto.AuthRequestDTO;
import com.nexus.auth.model.dto.UserRequestDTO;
import com.nexus.auth.model.dto.UserResponseDTO;
import com.nexus.auth.model.mapper.UserMapper;
import com.nexus.auth.repository.UserRepository;
import com.nexus.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper mapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserResponseDTO create(UserRequestDTO user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return mapper.toDTO(userRepository.save(mapper.toModel(user)));
    }

    public Page<UserResponseDTO> findAll(Pageable pageable) {
        Page<User> pageUser = userRepository.findAll(pageable);
        return new PageImpl<UserResponseDTO>(pageUser.stream()
                .map(user -> mapper.toDTO(user))
                .collect(Collectors.toList()),
                pageable,
                pageUser.getNumberOfElements());
    }

    public Optional<UserResponseDTO> findById(UUID id) {
        Optional<User> user = userRepository.findById(id);
        return Optional.ofNullable(mapper.toDTO(user.orElseThrow()));
    }

    public UserResponseDTO update(UUID id, UserRequestDTO user) {
        User entity = mapper.toModel(user);
        entity.setId(id);
        if (user.getPassword().isEmpty()) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        } else {
            entity.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return mapper.toDTO(userRepository.save(entity));
    }

    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public UserResponseDTO login(AuthRequestDTO credentials) throws Exception {
        User user = userRepository.findByEmail(credentials.getEmail());
        var passwordMatches = passwordEncoder.matches(credentials.getPassword(), user.getPassword());
        if (!passwordMatches) throw new Exception();
        return mapper.toDTO(user);
    }
}
