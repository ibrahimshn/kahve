package com.kahve.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "brews")
public class Brew {
    @Id
    private String id;

    @Indexed
    private String userId;

    @Indexed
    private String coffeeId;

    private BrewMethod method;
    private Double coffeeAmount; // Grams
    private Double waterAmount; // Milliliters/Grams
    private Integer temperature; // Celsius
    private Integer brewTimeSeconds; // Total brew time in seconds
    private String grindSetting; // E.g., "15 clicks", "Medium-Fine"
    private Integer rating; // 1-5 scale
    private String notes;

    @Builder.Default
    private Instant brewedAt = Instant.now();
}

