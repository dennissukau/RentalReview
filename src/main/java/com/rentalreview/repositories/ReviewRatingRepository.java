package com.rentalreview.repositories;

import com.rentalreview.entities.Review;
import com.rentalreview.entities.ReviewRating;
import com.rentalreview.entities.ReviewRatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRatingRepository extends JpaRepository <ReviewRating, ReviewRatingId> {
    List<ReviewRating> findByReview(Review review);
}
