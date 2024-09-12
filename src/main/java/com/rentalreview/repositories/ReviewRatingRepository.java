package com.rentalreview.repositories;

import com.rentalreview.entities.ReviewRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRatingRepository extends JpaRepository <ReviewRating, Long> {
}
