package com.kahve.controller;

import com.kahve.dto.response.ApiResponse;
import com.kahve.dto.response.UserListResponse;
import com.kahve.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ApiResponse<UserListResponse> getAllUsers() {
        return ApiResponse.success(userService.getAllUsers());
    }
}
