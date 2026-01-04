package com.kahve.dto.response;

import java.util.Set;

public record MeResponse(
        String id,
        String email,
        Set<String> roles
) {}

