package com.kahve.dto.response;

import java.util.List;

public record UserListResponse(
    List<UserResponse> users
) {}

