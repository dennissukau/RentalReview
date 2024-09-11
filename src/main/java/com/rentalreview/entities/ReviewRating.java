package com.rentalreview.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ReviewRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "criteria_id")
    private RatingCriteria criteria;

    private int rating;  // Rating between 0 and 10

    @Column(columnDefinition = "TEXT")
    private String comment;  // Additional comments

}
