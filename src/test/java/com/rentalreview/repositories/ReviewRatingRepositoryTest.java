package com.rentalreview.repositories;

import com.rentalreview.config.TestBase;
import com.rentalreview.data.*;
import com.rentalreview.entities.ReviewRating;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ReviewRatingRepositoryTest extends TestBase {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ReviewRatingRepository reviewRatingRepository;

    @Autowired
    private RatingCriteriaRepository ratingCriteriaRepository;

    @AfterEach
    public void cleanUp() {
        reviewRatingRepository.deleteAll();
        ratingCriteriaRepository.deleteAll();
        reviewRepository.deleteAll();
        propertyRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateAReviewRatingSuccessfully() {
        //Create user and property

        var user = UserGenerator.randomUser();
        var property = PropertyGenerator.randomProperty(user);

        //Save in repository
        userRepository.save(user);
        propertyRepository.save(property);

        //Create a review with the user and property entities created above
        var review = ReviewGenerator.randomReview(user, property);
        reviewRepository.save(review);

        //Creation of ratingCriteria
        var ratingCriteria = RatingCriteriaGenerator.randomReviewCriteria();
        ratingCriteriaRepository.save(ratingCriteria);

        //Creation of ReviewRating and adding it to the Review
        var reviewrating = ReviewRatingGenerator.randomReviewRating(review, ratingCriteria);
        Set<ReviewRating> ratingSetForReview = new HashSet<>();
        ratingSetForReview.add(reviewrating);
        review.setReviewRatings(ratingSetForReview);

        reviewRatingRepository.save(reviewrating);

        var reviews = reviewRepository.findAll();
        var reviewRatings = reviewRatingRepository.findAll();

        assertThat(reviews).hasSize(1);
        assertThat(reviewRatings).hasSize(1);
    }
}