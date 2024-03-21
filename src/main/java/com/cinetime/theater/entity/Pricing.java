package com.cinetime.theater.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Pricing")
public class Pricing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pricing_id")
    private Long pricingId;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @Column(name = "seat_type")
    private String seatType;

    @Column(name = "show_time")
    private LocalDateTime showTime;

    private BigDecimal price;


    @Column(name = "created_at")
    @CreationTimestamp
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    // Constructors, getters, and setters
}
