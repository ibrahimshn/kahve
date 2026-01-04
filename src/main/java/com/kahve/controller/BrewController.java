package com.kahve.controller;

import com.kahve.dto.BrewRequest;
import com.kahve.dto.response.ApiResponse;
import com.kahve.dto.response.BrewListResponse;
import com.kahve.dto.response.BrewResponse;
import com.kahve.service.BrewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brews")
@RequiredArgsConstructor
public class BrewController {
    private final BrewService brewService;

    @PostMapping
    public ApiResponse<BrewResponse> createBrew(@AuthenticationPrincipal Jwt jwt,
                                                @Valid @RequestBody BrewRequest req) {
        String userId = jwt.getSubject();
        return ApiResponse.success(brewService.createBrew(userId, req));
    }

    @GetMapping
    public ApiResponse<BrewListResponse> getMyBrews(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        return ApiResponse.success(brewService.getMyBrews(userId));
    }

    @GetMapping("/{id}")
    public ApiResponse<BrewResponse> getBrew(@AuthenticationPrincipal Jwt jwt,
                                             @PathVariable String id) {
        String userId = jwt.getSubject();
        return ApiResponse.success(brewService.getBrew(userId, id));
    }

    @PutMapping("/{id}")
    public ApiResponse<BrewResponse> updateBrew(@AuthenticationPrincipal Jwt jwt,
                                                @PathVariable String id,
                                                @Valid @RequestBody BrewRequest req) {
        String userId = jwt.getSubject();
        return ApiResponse.success(brewService.updateBrew(userId, id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBrew(@AuthenticationPrincipal Jwt jwt,
                                        @PathVariable String id) {
        String userId = jwt.getSubject();
        brewService.deleteBrew(userId, id);
        return ApiResponse.success(null);
    }
}

