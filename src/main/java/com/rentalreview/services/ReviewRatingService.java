package com.rentalreview.services;

import com.rentalreview.dto.ReviewRatingDto;
import com.rentalreview.entities.Review;
import com.rentalreview.entities.ReviewRating;
import com.rentalreview.entities.ReviewRatingId;
import com.rentalreview.repositories.RatingCriteriaRepository;
import com.rentalreview.repositories.ReviewRatingRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReviewRatingService {

    private final RatingCriteriaRepository ratingCriteriaRepository;
    private final ReviewRatingRepository reviewRatingRepository;

    @Transactional
    public Set<ReviewRating> createReviewRatings(List<ReviewRatingDto> reviewRequest, Review savedReview) {

        return reviewRequest.stream()
                .map(reviewRatingDto -> {
                    var reviewRating = new ReviewRating();
                    var reviewCriteria = ratingCriteriaRepository.findById(reviewRatingDto.getCriteriaId()).orElseThrow(() -> new IllegalArgumentException("Invalid criterion ID"));
                    var id = new ReviewRatingId(savedReview.getId(), reviewRatingDto.getCriteriaId());
                    reviewRating.setReviewRatingId(id);
                    reviewRating.setReview(savedReview);
                    reviewRating.setRatingCriteria(reviewCriteria);
                    reviewRating.setComment(reviewRatingDto.getComment());
                    reviewRating.setRating(reviewRatingDto.getScore());

                    return reviewRating;
                }).collect(Collectors.toSet());
    }

    @Transactional
    public ReviewRating updateToReviewRating(Long reviewId, ReviewRatingDto updatedReviewRating){
        var id = new ReviewRatingId(reviewId, updatedReviewRating.getCriteriaId());

        var reviewRatingToUpdate = reviewRatingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("This Rating was not found"));

        reviewRatingToUpdate.setRating(updatedReviewRating.getScore());
        reviewRatingToUpdate.setComment(updatedReviewRating.getComment());

        return reviewRatingRepository.save(reviewRatingToUpdate);
    }
}

