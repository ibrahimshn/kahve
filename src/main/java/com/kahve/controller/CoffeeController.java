package com.kahve.controller;

import com.kahve.dto.CoffeeRequest;
import com.kahve.dto.response.ApiResponse;
import com.kahve.dto.response.CoffeeListResponse;
import com.kahve.dto.response.CoffeeResponse;
import com.kahve.service.CoffeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coffees")
@RequiredArgsConstructor
public class CoffeeController {
    private final CoffeeService coffeeService;

    @PostMapping
    public ApiResponse<CoffeeResponse> createCoffee(@AuthenticationPrincipal Jwt jwt,
                                                    @Valid @RequestBody CoffeeRequest req) {
        String userId = jwt.getSubject();
        return ApiResponse.success(coffeeService.createCoffee(userId, req));
    }

    @GetMapping
    public ApiResponse<CoffeeListResponse> getMyCoffees(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        return ApiResponse.success(coffeeService.getMyCoffees(userId));
    }

    @GetMapping("/{id}")
    public ApiResponse<CoffeeResponse> getCoffee(@AuthenticationPrincipal Jwt jwt,
                                                 @PathVariable String id) {
        String userId = jwt.getSubject();
        return ApiResponse.success(coffeeService.getCoffee(userId, id));
    }

    @PutMapping("/{id}")
    public ApiResponse<CoffeeResponse> updateCoffee(@AuthenticationPrincipal Jwt jwt,
                                                    @PathVariable String id,
                                                    @Valid @RequestBody CoffeeRequest req) {
        String userId = jwt.getSubject();
        return ApiResponse.success(coffeeService.updateCoffee(userId, id, req));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCoffee(@AuthenticationPrincipal Jwt jwt,
                                          @PathVariable String id) {
        String userId = jwt.getSubject();
        coffeeService.deleteCoffee(userId, id);
        return ApiResponse.success(null);
    }
}

