package com.kahve.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "coffees")
public class Coffee {
    @Id
    private String id;

    @Indexed
    private String userId;

    private String brand; // Marka
    private Double initialWeight; // Gramaj (Başlangıç)
    private Double remainingWeight; // Kalan Gramaj
    private String country; // Ülke
    private String region; // Yöre
    private String variety; // Varyete
    private String farm; // Çiftlik
    private LocalDate purchaseDate; // Satın alım tarihi
    private Integer altitude; // Rakım
    private String harvest; // Hasat
    private List<String> tastingNotes; // Tadım notları
    private String personalNotes; // Kendi düşünceleri
    private BigDecimal price; // Fiyat
    private String currency; // Para birimi (TRY, USD vs) - Opsiyonel ekledim

    @Builder.Default
    private Instant createdAt = Instant.now();
    @Builder.Default
    private Instant updatedAt = Instant.now();
}

