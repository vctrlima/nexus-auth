package com.nexus.auth.service.impl;

import com.nexus.auth.model.dto.UserResponseDTO;
import com.nexus.auth.model.mapper.UserMapper;
import com.nexus.auth.service.JWTService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTServiceImpl implements JWTService {
    @Autowired
    UserMapper mapper;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${app.jwttoken.message}")
    private String message;

    @Override
    public Map<String, String> generateToken(UserResponseDTO user) {
        String jwtToken = Jwts.builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        Map<String, String> jwtTokenGen = new HashMap<>();
        jwtTokenGen.put("token", jwtToken);
        jwtTokenGen.put("id", user.getId().toString());
        jwtTokenGen.put("email", user.getEmail());
        return jwtTokenGen;
    }
}
