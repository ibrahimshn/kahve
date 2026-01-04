package com.kahve.service;

import com.kahve.dto.BrewRequest;
import com.kahve.dto.response.BrewListResponse;
import com.kahve.dto.response.BrewResponse;
import com.kahve.model.Brew;
import com.kahve.model.Coffee;
import com.kahve.repository.BrewRepository;
import com.kahve.repository.CoffeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BrewService {
    private final BrewRepository brewRepository;
    private final CoffeeRepository coffeeRepository;

    @Transactional
    public BrewResponse createBrew(String userId, BrewRequest req) {
        Coffee coffee = getCoffeeOrThrow(req.coffeeId(), userId);

        // Deduct coffee amount
        if (coffee.getRemainingWeight() < req.coffeeAmount()) {
            throw new IllegalArgumentException("Not enough coffee remaining. Available: " + coffee.getRemainingWeight());
        }
        coffee.setRemainingWeight(coffee.getRemainingWeight() - req.coffeeAmount());
        coffeeRepository.save(coffee);

        Brew brew = Brew.builder()
                .userId(userId)
                .coffeeId(req.coffeeId())
                .method(req.method())
                .coffeeAmount(req.coffeeAmount())
                .waterAmount(req.waterAmount())
                .temperature(req.temperature())
                .brewTimeSeconds(req.brewTimeSeconds())
                .grindSetting(req.grindSetting())
                .rating(req.rating())
                .notes(req.notes())
                .brewedAt(req.brewedAt() != null ? req.brewedAt() : Instant.now())
                .build();

        Brew saved = brewRepository.save(brew);
        return mapToResponse(saved, coffee);
    }

    public BrewListResponse getMyBrews(String userId) {
        List<Brew> brews = brewRepository.findAllByUserIdOrderByBrewedAtDesc(userId);
        List<BrewResponse> responses = brews.stream()
                .map(brew -> {
                    // We might not want to fail if coffee is deleted, so handle gracefully
                    Coffee coffee = coffeeRepository.findById(brew.getCoffeeId()).orElse(null);
                    return mapToResponse(brew, coffee);
                })
                .toList();
        return new BrewListResponse(responses);
    }

    public BrewResponse getBrew(String userId, String brewId) {
        Brew brew = getBrewOrThrow(brewId, userId);
        Coffee coffee = coffeeRepository.findById(brew.getCoffeeId()).orElse(null);
        return mapToResponse(brew, coffee);
    }

    @Transactional
    public BrewResponse updateBrew(String userId, String brewId, BrewRequest req) {
        Brew brew = getBrewOrThrow(brewId, userId);

        // If coffee ID changed
        if (!brew.getCoffeeId().equals(req.coffeeId())) {
            // Revert old coffee amount
            Coffee oldCoffee = coffeeRepository.findById(brew.getCoffeeId()).orElse(null);
            if (oldCoffee != null) {
                oldCoffee.setRemainingWeight(oldCoffee.getRemainingWeight() + brew.getCoffeeAmount());
                coffeeRepository.save(oldCoffee);
            }

            // Deduct new coffee amount
            Coffee newCoffee = getCoffeeOrThrow(req.coffeeId(), userId);
            if (newCoffee.getRemainingWeight() < req.coffeeAmount()) {
                throw new IllegalArgumentException("Not enough coffee remaining in new coffee. Available: " + newCoffee.getRemainingWeight());
            }
            newCoffee.setRemainingWeight(newCoffee.getRemainingWeight() - req.coffeeAmount());
            coffeeRepository.save(newCoffee);
        } else {
            // Same coffee, check amount difference
            double diff = req.coffeeAmount() - brew.getCoffeeAmount();
            if (diff != 0) {
                Coffee coffee = getCoffeeOrThrow(brew.getCoffeeId(), userId);
                if (coffee.getRemainingWeight() < diff) {
                    throw new IllegalArgumentException("Not enough coffee remaining for increase. Available: " + coffee.getRemainingWeight());
                }
                coffee.setRemainingWeight(coffee.getRemainingWeight() - diff);
                coffeeRepository.save(coffee);
            }
        }

        brew.setCoffeeId(req.coffeeId());
        brew.setMethod(req.method());
        brew.setCoffeeAmount(req.coffeeAmount());
        brew.setWaterAmount(req.waterAmount());
        brew.setTemperature(req.temperature());
        brew.setBrewTimeSeconds(req.brewTimeSeconds());
        brew.setGrindSetting(req.grindSetting());
        brew.setRating(req.rating());
        brew.setNotes(req.notes());
        if (req.brewedAt() != null) {
            brew.setBrewedAt(req.brewedAt());
        }

        Brew updated = brewRepository.save(brew);
        Coffee currentCoffee = coffeeRepository.findById(updated.getCoffeeId()).orElse(null);
        return mapToResponse(updated, currentCoffee);
    }

    @Transactional
    public void deleteBrew(String userId, String brewId) {
        Brew brew = getBrewOrThrow(brewId, userId);

        // Revert coffee amount
        Coffee coffee = coffeeRepository.findById(brew.getCoffeeId()).orElse(null);
        if (coffee != null) {
            coffee.setRemainingWeight(coffee.getRemainingWeight() + brew.getCoffeeAmount());
            coffeeRepository.save(coffee);
        }

        brewRepository.delete(brew);
    }

    private Brew getBrewOrThrow(String brewId, String userId) {
        return brewRepository.findByIdAndUserId(brewId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Brew not found or access denied"));
    }

    private Coffee getCoffeeOrThrow(String coffeeId, String userId) {
        return coffeeRepository.findByIdAndUserId(coffeeId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Coffee not found or access denied"));
    }

    private BrewResponse mapToResponse(Brew b, Coffee c) {
        return new BrewResponse(
                b.getId(),
                b.getCoffeeId(),
                c != null ? c.getBrand() : "Unknown Coffee",
                c != null ? c.getCountry() : "Unknown Origin",
                b.getMethod(),
                b.getCoffeeAmount(),
                b.getWaterAmount(),
                b.getTemperature(),
                b.getBrewTimeSeconds(),
                b.getGrindSetting(),
                b.getRating(),
                b.getNotes(),
                b.getBrewedAt()
        );
    }
}

