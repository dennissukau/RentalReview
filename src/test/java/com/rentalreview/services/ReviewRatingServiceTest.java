package com.rentalreview.services;

import com.rentalreview.config.TestBase;
import com.rentalreview.data.*;
import com.rentalreview.dto.ReviewRatingDto;
import com.rentalreview.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ReviewRatingServiceTest extends TestBase {
    @Autowired
    private ReviewRatingService reviewRatingService;

    @Autowired
    private ReviewRatingRepository reviewRatingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RatingCriteriaRepository ratingCriteriaRepository;

    private List<ReviewRatingDto> testReviewRatingDto;

    private TestDataGenerator testDataGenerator;

    @BeforeEach
    void setUp() {
        testDataGenerator = new TestDataGenerator(userRepository, propertyRepository, reviewRepository, ratingCriteriaRepository);
        testDataGenerator.populateRatingCriteria(4);

        IntStream.range(0, 5).forEach(i -> {
            var testUser = testDataGenerator.createUser();
            var testProperty = testDataGenerator.createProperty(testUser);
            testDataGenerator.createReview(testUser, testProperty);
        });

        testReviewRatingDto = testDataGenerator.generateReviewRatingDtos(ratingCriteriaRepository);
    }

    @AfterEach
    public void cleanUp() {
        reviewRatingRepository.deleteAll();
        ratingCriteriaRepository.deleteAll();
        propertyRepository.deleteAll();
        userRepository.deleteAll();
        reviewRepository.deleteAll();
    }

    @Test
    void createReviewRatings() {

        var testReview = reviewRepository.findAll().get(0);
        var testReviewRating = reviewRatingService.createReviewRatings(testReviewRatingDto, testReview);

        assertThat(testReviewRating).isNotEmpty();
        assertThat(testReviewRating.size()).isEqualTo(4);
    }

    @Test
    void updateToReviewRating() {
        //Creates Reviews with a List of ReviewRatings
        var testReview = reviewRepository.findAll().get(0);
        var testReviewRating = reviewRatingService.createReviewRatings(testReviewRatingDto, testReview);
        reviewRatingRepository.saveAll(testReviewRating);

        var randomReview = reviewRepository.findAll().get(0);
        var ratings = reviewRatingRepository.findByReview(randomReview);
        assertThat(ratings).isNotEmpty();

        var ratingToUpdate = ratings.get(0);
        var currentRating = ratingToUpdate.getRating();
        var newRating = currentRating + 1;

        var ratingDtoUsedForUpdate = new ReviewRatingDto(
                ratingToUpdate.getRatingCriteria().getId(),
                newRating,
                "new Comment");

        var updatedReviewRating = reviewRatingService.updateToReviewRating(randomReview.getId(), ratingDtoUsedForUpdate);


        assertThat(updatedReviewRating.getRating()).isEqualTo(newRating);
        assertThat(updatedReviewRating.getReview()).isEqualTo(randomReview);
        assertThat(updatedReviewRating.getReviewRatingId()).isEqualTo(ratingToUpdate.getReviewRatingId());
    }
}