package com.kahve.dto.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record CoffeeResponse(
    String id,
    String brand,
    Double initialWeight,
    Double remainingWeight,
    String country,
    String region,
    String variety,
    String farm,
    LocalDate purchaseDate,
    Integer altitude,
    String harvest,
    List<String> tastingNotes,
    String personalNotes,
    BigDecimal price,
    String currency,
    FreshnessStatus freshness,
    Instant createdAt,
    Instant updatedAt
) {}

