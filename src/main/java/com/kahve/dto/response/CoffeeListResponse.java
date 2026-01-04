package com.kahve.dto.response;

import java.util.List;

public record CoffeeListResponse(
    List<CoffeeResponse> coffees
) {}

