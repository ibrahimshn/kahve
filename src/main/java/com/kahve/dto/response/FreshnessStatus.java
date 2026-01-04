package com.kahve.dto.response;

public record FreshnessStatus(
    String label,
    String colorCode,
    long daysSinceRoast // or purchase
) {}

