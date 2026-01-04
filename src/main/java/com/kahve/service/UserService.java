package com.kahve.service;

import com.kahve.dto.response.UserListResponse;
import com.kahve.dto.response.UserResponse;
import com.kahve.model.User;
import com.kahve.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserListResponse getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> userResponses = users.stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getEmail(),
                        u.getName(),
                        u.getSurname(),
                        u.getRoles(),
                        u.getCreatedAt()
                ))
                .toList();
        return new UserListResponse(userResponses);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
