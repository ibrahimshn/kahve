package com.kahve.dto.response;

import java.util.List;

public record BrewListResponse(
    List<BrewResponse> brews
) {}

