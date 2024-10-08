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

    @EmbeddedId
    private ReviewRatingId reviewRatingId;

    @ManyToOne
    @MapsId("reviewId")
    @JoinColumn(name = "review_id")
    private Review review;

    @ManyToOne
    @MapsId("ratingCriteriaId")
    @JoinColumn(name = "rating_criteria_id")
    private RatingCriteria ratingCriteria;

    private int rating;  // Rating between 0 and 10

    @Column(columnDefinition = "TEXT")
    private String comment;  // Additional comments

}
