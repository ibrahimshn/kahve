package com.kahve.service;

import com.kahve.dto.CoffeeRequest;
import com.kahve.dto.response.CoffeeListResponse;
import com.kahve.dto.response.CoffeeResponse;
import com.kahve.dto.response.FreshnessStatus;
import com.kahve.model.Coffee;
import com.kahve.repository.CoffeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CoffeeService {
    private final CoffeeRepository coffeeRepository;

    public CoffeeResponse createCoffee(String userId, CoffeeRequest req) {
        Coffee coffee = Coffee.builder()
                .userId(userId)
                .brand(req.brand())
                .initialWeight(req.initialWeight())
                .remainingWeight(req.remainingWeight() != null ? req.remainingWeight() : req.initialWeight())
                .country(req.country())
                .region(req.region())
                .variety(req.variety())
                .farm(req.farm())
                .purchaseDate(req.purchaseDate())
                .altitude(req.altitude())
                .harvest(req.harvest())
                .tastingNotes(req.tastingNotes())
                .personalNotes(req.personalNotes())
                .price(req.price())
                .currency(req.currency())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Coffee saved = coffeeRepository.save(coffee);
        return mapToResponse(saved);
    }

    public CoffeeListResponse getMyCoffees(String userId) {
        List<Coffee> coffees = coffeeRepository.findAllByUserId(userId);
        List<CoffeeResponse> responses = coffees.stream()
                .map(this::mapToResponse)
                .toList();
        return new CoffeeListResponse(responses);
    }

    public CoffeeResponse getCoffee(String userId, String coffeeId) {
        Coffee coffee = getCoffeeOrThrow(userId, coffeeId);
        return mapToResponse(coffee);
    }

    public CoffeeResponse updateCoffee(String userId, String coffeeId, CoffeeRequest req) {
        Coffee coffee = getCoffeeOrThrow(userId, coffeeId);

        coffee.setBrand(req.brand());
        coffee.setInitialWeight(req.initialWeight());
        if (req.remainingWeight() != null) {
            coffee.setRemainingWeight(req.remainingWeight());
        }
        coffee.setCountry(req.country());
        coffee.setRegion(req.region());
        coffee.setVariety(req.variety());
        coffee.setFarm(req.farm());
        coffee.setPurchaseDate(req.purchaseDate());
        coffee.setAltitude(req.altitude());
        coffee.setHarvest(req.harvest());
        coffee.setTastingNotes(req.tastingNotes());
        coffee.setPersonalNotes(req.personalNotes());
        coffee.setPrice(req.price());
        coffee.setCurrency(req.currency());
        coffee.setUpdatedAt(Instant.now());

        Coffee updated = coffeeRepository.save(coffee);
        return mapToResponse(updated);
    }

    public void deleteCoffee(String userId, String coffeeId) {
        Coffee coffee = getCoffeeOrThrow(userId, coffeeId);
        coffeeRepository.delete(coffee);
    }

    private Coffee getCoffeeOrThrow(String userId, String coffeeId) {
        return coffeeRepository.findByIdAndUserId(coffeeId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Coffee not found or access denied"));
    }

    private CoffeeResponse mapToResponse(Coffee c) {
        return new CoffeeResponse(
                c.getId(),
                c.getBrand(),
                c.getInitialWeight(),
                c.getRemainingWeight(),
                c.getCountry(),
                c.getRegion(),
                c.getVariety(),
                c.getFarm(),
                c.getPurchaseDate(),
                c.getAltitude(),
                c.getHarvest(),
                c.getTastingNotes(),
                c.getPersonalNotes(),
                c.getPrice(),
                c.getCurrency(),
                calculateFreshness(c.getPurchaseDate()),
                c.getCreatedAt(),
                c.getUpdatedAt()
        );
    }

    private FreshnessStatus calculateFreshness(LocalDate purchaseDate) {
        if (purchaseDate == null) {
            return null;
        }

        long days = ChronoUnit.DAYS.between(purchaseDate, LocalDate.now());
        String label;
        String color;

        if (days <= 21) {
            label = "Taze";
            color = "#28a745"; // Green
        } else if (days <= 45) {
            label = "Olgun";
            color = "#ffc107"; // Yellow
        } else if (days <= 75) {
            label = "Yaşlanıyor";
            color = "#fd7e14"; // Orange
        } else {
            label = "Bayat";
            color = "#dc3545"; // Red
        }

        return new FreshnessStatus(label, color, days);
    }
}
