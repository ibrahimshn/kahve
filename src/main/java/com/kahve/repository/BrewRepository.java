package com.kahve.repository;

import com.kahve.model.Brew;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrewRepository extends MongoRepository<Brew, String> {
    List<Brew> findAllByUserIdOrderByBrewedAtDesc(String userId);
    Optional<Brew> findByIdAndUserId(String id, String userId);
    List<Brew> findAllByCoffeeId(String coffeeId);
}

