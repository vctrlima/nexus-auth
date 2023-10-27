package com.nexus.auth.service;

import com.nexus.auth.model.User;
import com.nexus.auth.model.dto.UserResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface JWTService {
    public Map<String, String> generateToken(UserResponseDTO user);
}
