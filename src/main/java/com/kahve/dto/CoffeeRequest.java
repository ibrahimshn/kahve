package com.kahve.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CoffeeRequest(
    @NotBlank(message = "Brand is required")
    String brand,

    @NotNull(message = "Initial weight is required")
    @Positive(message = "Initial weight must be positive")
    Double initialWeight,

    @PositiveOrZero(message = "Remaining weight must be positive or zero")
    Double remainingWeight,

    @NotBlank(message = "Country is required")
    String country,

    String region,
    String variety,
    String farm,
    LocalDate purchaseDate,
    Integer altitude,
    String harvest,
    List<String> tastingNotes,
    String personalNotes,

    @PositiveOrZero(message = "Price must be positive or zero")
    BigDecimal price,

    String currency
) {}

