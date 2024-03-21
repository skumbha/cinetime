package com.cinetime.theater.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "Screen")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "screen_id")
    private Long screenId;

    private Long theaterId;

    @Column(name = "screen_number")
    private Integer screenNumber;

    private Integer capacity;

    @Column(name = "screen_type")
    private String screenType; // 2D, 3D, IMAX

    @Column(name = "created_at")
    @CreationTimestamp
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt;

    // Getters and setters
}
