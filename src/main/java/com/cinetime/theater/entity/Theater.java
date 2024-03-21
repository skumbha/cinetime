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
@Table(name = "Theater")
@Data @AllArgsConstructor @NoArgsConstructor
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "theater_id")
    private Long theaterId;

    private String name;
    private String address;
    private String city;
    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    //@ManyToOne
    //@JoinColumn(name = "partner_id")
    //private TheaterPartner partner;

    private Long partnerId;

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
