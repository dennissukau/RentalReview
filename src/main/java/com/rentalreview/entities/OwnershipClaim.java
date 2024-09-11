package com.rentalreview.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OwnershipClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    private String documentUrl;
    private String status;
    private LocalDateTime submittedAt;
    private LocalDateTime verifiedAt;

}

