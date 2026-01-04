package com.kahve.repository;

import com.kahve.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    // Giriş işlemleri için e-posta ile kullanıcı bulma
    Optional<User> findByEmail(String email);

    // Kayıt sırasında e-postanın daha önce kullanılıp kullanılmadığını kontrol etme
    boolean existsByEmail(String email);
}

