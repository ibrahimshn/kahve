package com.kahve.service;

import com.kahve.dto.response.AuthResponse;
import com.kahve.dto.LoginRequest;
import com.kahve.dto.response.MeResponse;
import com.kahve.dto.RegisterRequest;
import com.kahve.model.User;
import com.kahve.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    public void register(RegisterRequest req) {
        String email = req.email().trim().toLowerCase();

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already exists");
        }

        User u = new User();
        u.setEmail(email);
        u.setPasswordHash(passwordEncoder.encode(req.password()));
        u.setName(req.name());
        u.setSurname(req.surname());
        u.setEnabled(true);

        userRepository.save(u);
    }

    public AuthResponse login(LoginRequest req) {
        String email = req.email().trim().toLowerCase();

        User u = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!u.isEnabled()) {
            throw new IllegalArgumentException("User is disabled");
        }

        if (!passwordEncoder.matches(req.password(), u.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        var token = tokenService.createAccessToken(u);
        return new AuthResponse(token.token(), "Bearer", token.expiresInSeconds());
    }

    public MeResponse me(Jwt jwt) {
        String userId = jwt.getSubject();
        String email = jwt.getClaimAsString("email");
        var roles = new java.util.HashSet<>(jwt.getClaimAsStringList("roles"));
        return new MeResponse(userId, email, roles);
    }
}
