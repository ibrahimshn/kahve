package com.kahve.repository;

import com.kahve.model.Coffee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoffeeRepository extends MongoRepository<Coffee, String> {
    List<Coffee> findAllByUserId(String userId);
    Optional<Coffee> findByIdAndUserId(String id, String userId);
}

