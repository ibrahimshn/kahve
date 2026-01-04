package com.kahve.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    @Indexed(unique = true)
    private String email;
    private String passwordHash;
    @Builder.Default
    private boolean enabled = true;
    private String name;
    private String surname;
    @Builder.Default
    private Set<String> roles = Set.of("USER");
    @Builder.Default
    private Instant createdAt = Instant.now();
}
