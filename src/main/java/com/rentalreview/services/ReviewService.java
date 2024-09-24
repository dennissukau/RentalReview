package com.rentalreview.services;

import com.rentalreview.dto.ReviewRequestDto;
import com.rentalreview.entities.Property;
import com.rentalreview.entities.Review;
import com.rentalreview.entities.ReviewRating;
import com.rentalreview.entities.User;
import com.rentalreview.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final ReviewRatingRepository reviewRatingRepository;
    private final RatingCriteriaRepository ratingCriteriaRepository;
    private final ReviewRatingService reviewRatingService;

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsByProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));
        return reviewRepository.findByProperty(property);
    }

    public List<Review> getReviewsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return reviewRepository.findByUser(user);
    }

    @Transactional
    public Review createReview(ReviewRequestDto reviewRequest) {
        //Find the user first
        User user = userRepository.findById(reviewRequest.getUserID()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        //Find the property
        Property property = propertyRepository.findById(reviewRequest.getPropertyID()).orElseThrow(() -> new IllegalArgumentException("Invalid property ID"));
        //Create a review instance and add it to the database
        var review = Review.builder()
                .tenant(user)
                .property(property)
                .build();
        //Save the review in the database
        var savedReview = reviewRepository.save(review);
        //Populate the List within review with the ratings
        var reviewRatings = reviewRatingService.createReviewRatings(reviewRequest, savedReview);
        //Save all the reviewRatings of this review
        reviewRatingRepository.saveAll((reviewRatings));

        return savedReview;
    }
}