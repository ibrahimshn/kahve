package com.kahve.dto;

import com.kahve.model.BrewMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.Instant;

public record BrewRequest(
    @NotNull(message = "Coffee ID is required")
    String coffeeId,

    @NotNull(message = "Brew method is required")
    BrewMethod method,

    @NotNull(message = "Coffee amount is required")
    @Positive(message = "Coffee amount must be positive")
    Double coffeeAmount,

    @Positive(message = "Water amount must be positive")
    Double waterAmount,

    @Min(value = 0, message = "Temperature cannot be negative")
    @Max(value = 100, message = "Temperature cannot exceed 100°C")
    Integer temperature,

    @Min(value = 0, message = "Brew time cannot be negative")
    Integer brewTimeSeconds,

    String grindSetting,

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    Integer rating,

    String notes,

    Instant brewedAt
) {}

