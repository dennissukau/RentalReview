package com.rentalreview.entities;


import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ReviewRatingId implements Serializable {

    private Long reviewId;
    private Long ratingCriteriaId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReviewRatingId that = (ReviewRatingId) o;
        return Objects.equals(reviewId, that.reviewId) &&
                Objects.equals(ratingCriteriaId, that.ratingCriteriaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, ratingCriteriaId);
    }
}
