package com.rentalreview.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Property extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    private String address;
    private String city;
    private String state;
    private String postcode;
    private String propertyType;
    private int bedrooms;
    private int bathrooms;
    private int parkingSpaces;
    private int squareMeters;
    private String description;
}
