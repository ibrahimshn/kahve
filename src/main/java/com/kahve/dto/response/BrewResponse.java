package com.kahve.dto.response;

import com.kahve.model.BrewMethod;
import java.time.Instant;

public record BrewResponse(
    String id,
    String coffeeId,
    String coffeeBrand, // Extra info for UI convenience
    String coffeeOrigin, // Extra info for UI convenience
    BrewMethod method,
    Double coffeeAmount,
    Double waterAmount,
    Integer temperature,
    Integer brewTimeSeconds,
    String grindSetting,
    Integer rating,
    String notes,
    Instant brewedAt
) {}

