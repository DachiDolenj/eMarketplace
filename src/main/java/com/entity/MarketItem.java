package com.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "market_item")
public class MarketItem {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "name is required")
    private String name;

    @NotNull(message = "price is required")
    private BigDecimal price;

    @Column(length = 1000)
    private String description;

    @Column(name = "submittion_time")
    private LocalDateTime submissionTime;

    @NotNull(message = "photoUrl is required")
    private String photoUrl;
}
