package com.kahve.dto.response;

import java.time.Instant;
import java.util.Set;

public record UserResponse(
    String id,
    String email,
    String name,
    String surname,
    Set<String> roles,
    Instant createdAt
) {}

