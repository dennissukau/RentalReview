package com.rentalreview.services;

import com.rentalreview.dto.ReviewDto;
import com.rentalreview.dto.ReviewRequestDto;
import com.rentalreview.entities.Property;
import com.rentalreview.entities.Review;
import com.rentalreview.entities.User;
import com.rentalreview.mapper.ReviewMapper;
import com.rentalreview.repositories.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final ReviewMapper reviewMapper;

    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Transactional
    public List<ReviewDto> getReviewsByProperty(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new IllegalArgumentException("Property not found"));
        var reviewsByProperty = reviewRepository.findByProperty(property);
        return reviewsByProperty.stream()
                .map(reviewMapper::reviewToReviewDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ReviewDto> getReviewsByUser(Long userId) {
        //Find user first
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        //Find all reviews associated with the user
        var reviews = reviewRepository.findByTenant(user);
        //return a ReviewDto List
        return reviews.stream()
                .map(reviewMapper::reviewToReviewDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ReviewDto getReviewById(Long reviewId) {
        var review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));
        return reviewMapper.reviewToReviewDto(review);
    }

    @Transactional
    public ReviewDto createReview(ReviewRequestDto reviewRequest) {
        //Find the user first
        User user = userRepository.findById(reviewRequest.getUserID()).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        //Find the property
        Property property = propertyRepository.findById(reviewRequest.getPropertyID()).orElseThrow(() -> new IllegalArgumentException("Invalid property ID"));
        //Create a review instance and add it to the database
        var review = Review.builder()
                .tenant(user)
                .property(property)
                .createdTimestamp(LocalDateTime.now())
                .build();
        //Populate the List within review with the ratings
        var reviewRatings = reviewRatingService.createReviewRatings(reviewRequest.getRatings(), review);
        //Save the review in the database with all Ratings for each Criterion
        review.setReviewRatings(reviewRatings);
        var savedReview = reviewRepository.save(review);
        //Save all the reviewRatings of this review
        reviewRatingRepository.saveAll((reviewRatings));

        return reviewMapper.reviewToReviewDto(savedReview);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        var reviewToDelete = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found"));

        reviewRepository.delete(reviewToDelete);
    }
}