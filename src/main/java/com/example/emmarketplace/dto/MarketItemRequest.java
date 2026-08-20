package com.example.emmarketplace.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.lang.ref.PhantomReference;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MarketItemRequest {

    @NotBlank(message = "name in required")
    private String name;

    @Column(name = "submittion_time")
    private LocalDateTime submissionTime;

    @NotNull(message = "price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "price must be positive")
    private BigDecimal price;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Photo URL is required")
    @URL(message = "Invalid URL")
    private String photoUrl;

}
