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
@Table(name = "Theater_Partner")
@Data @AllArgsConstructor @NoArgsConstructor
public class TheaterPartner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partner_id")
    private Long partnerId;

    private String name;
    private String email;
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;
    private String city;
    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    private String status; // Active, Inactive

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
