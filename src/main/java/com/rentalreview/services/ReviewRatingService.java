package com.rentalreview.services;

import com.rentalreview.dto.ReviewRequestDto;
import com.rentalreview.entities.Review;
import com.rentalreview.entities.ReviewRating;
import com.rentalreview.repositories.RatingCriteriaRepository;
import com.rentalreview.repositories.ReviewRatingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewRatingService {

    private final RatingCriteriaRepository ratingCriteriaRepository;

    public List<ReviewRating> createReviewRatings(ReviewRequestDto reviewRequest, Review savedReview) {

        return reviewRequest.getRatings().stream()
                .map(reviewRatingDto -> {
                    var reviewRating = new ReviewRating();
                    reviewRating.setReview(savedReview);
                    reviewRating.setCriteria(ratingCriteriaRepository.findById(reviewRatingDto.getCriteriaId()).orElseThrow(() -> new IllegalArgumentException("Invalid criterion ID")));
                    reviewRating.setComment(reviewRatingDto.getComment());
                    reviewRating.setRating(reviewRatingDto.getScore());

                    return reviewRating;
                }).toList();
    }
}
