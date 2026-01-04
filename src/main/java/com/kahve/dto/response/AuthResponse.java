package com.kahve.dto.response;

public record AuthResponse(
        String token,
        String type,
        long expiresInSeconds
) {}

