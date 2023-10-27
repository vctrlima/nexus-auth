package com.nexus.auth.controller;

import com.nexus.auth.model.dto.AuthRequestDTO;
import com.nexus.auth.model.dto.UserRequestDTO;
import com.nexus.auth.model.dto.UserResponseDTO;
import com.nexus.auth.service.JWTService;
import com.nexus.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    JWTService jwtService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody UserRequestDTO user) {
        return ResponseEntity.ok().body(userService.create(user));
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int take) {
        Pageable pageable = PageRequest.of(page, take);
        return ResponseEntity.ok().body(userService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UserResponseDTO>> findById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(userService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(@PathVariable UUID id, @RequestBody UserRequestDTO user) {
        return ResponseEntity.ok().body(userService.update(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity.HeadersBuilder<?> delete(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody AuthRequestDTO credentials) throws Exception {
        try {
            UserResponseDTO user = userService.login(credentials);
            Map<String, String> token = jwtService.generateToken(user);
            return ResponseEntity.ok().body(token);
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
