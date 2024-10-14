package com.rentalreview.services;

import com.rentalreview.config.TestBase;
import com.rentalreview.data.ReviewGenerator;
import com.rentalreview.data.TestDataGenerator;
import com.rentalreview.dto.ReviewDto;
import com.rentalreview.dto.ReviewRatingDto;
import com.rentalreview.dto.ReviewRequestDto;
import com.rentalreview.mapper.ReviewMapper;
import com.rentalreview.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceTest extends TestBase {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private ReviewRatingRepository reviewRatingRepository;

    @Autowired
    private RatingCriteriaRepository ratingCriteriaRepository;

    @Autowired
    private ReviewMapper reviewMapper;
    private TestDataGenerator testDataGenerator;

    private List<ReviewRatingDto> testReviewRatingDto;

    @BeforeEach
    void setUpTestData() {
        testDataGenerator = new TestDataGenerator(userRepository, propertyRepository, reviewRepository, ratingCriteriaRepository);
        testDataGenerator.populateRatingCriteria(4);
        assertThat(ratingCriteriaRepository.findAll().size()).isEqualTo(4);

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
    void shouldFindAll() {
        var reviews = reviewService.findAll();

        assertThat(reviews.size()).isEqualTo(5);
        assertThat(reviews).isNotEmpty();
    }

    @Test
    void shouldGetReviewsByProperty() {
        //Select random User and Property from Database and add a second review to the selected Property
        var randomUser = userRepository.findAll().get(0);
        var randomProperty = propertyRepository.findAll().get(0);
        var secondReview = ReviewGenerator.randomReview(randomUser, randomProperty);
        reviewRepository.save(secondReview);

        //Try to retrieve all Reviews from the Property selected above
        List<ReviewDto> reviews = reviewService.getReviewsByProperty(randomProperty.getId());

        //Check that Property has 2 Reviews and that the correct property was selected
        assertThat(reviews).isNotEmpty();
        assertThat(reviews.size()).isEqualTo(2);
        assertThat(reviews.get(0).getProperty().getId()).isEqualTo(randomProperty.getId());
    }

    @Test
    void shouldGetReviewsByUser() {

        var randomUser = userRepository.findAll().get(0);
        var randomProperty = propertyRepository.findAll().get(0);
        var secondReview = ReviewGenerator.randomReview(randomUser, randomProperty);
        reviewRepository.save(secondReview);

        // Try to retrieve all Reviews for the selected User
        List<ReviewDto> reviews = reviewService.getReviewsByUser(randomUser.getId());

        // Check that User has 2 Reviews and that the correct user was selected
        assertThat(reviews).isNotEmpty();
        assertThat(reviews.size()).isEqualTo(2);
        assertThat(reviews.get(0).getTenant().getId()).isEqualTo(randomUser.getId());
    }

    @Test
    void shouldGetReviewById() {
        var review = reviewRepository.findAll().get(1);
        ReviewDto reviewDto = reviewService.getReviewById(review.getId());

        assertThat(reviewDto).isNotNull();
        assertThat(reviewDto.getId()).isEqualTo(review.getId());
    }

    @Test
    void shouldCreateReview() {
        var testUser = userRepository.findAll().get(0);
        System.out.println("Users: " + testUser);

        var testProperty = propertyRepository.findAll().get(0);
        System.out.println("Properties: " + testProperty);

        var reviewRequestDto = new ReviewRequestDto(testUser.getId(), testProperty.getId(), testReviewRatingDto);
        ReviewDto createdReview = reviewService.createReview(reviewRequestDto);
        //Validate the result is not null and has correct properties
        assertThat(createdReview).isNotNull();
        assertThat(createdReview.getTenant().getId()).isEqualTo(testUser.getId());
        assertThat(createdReview.getProperty().getId()).isEqualTo(testProperty.getId());

        // Ensure that the review ratings were also saved correctly
        assertThat(createdReview.getReviewRatings()).hasSize(testReviewRatingDto.size());

        // Verify that the created review exists in the database by checking with the repository
        var savedReview = reviewRepository.findById(createdReview.getId());
        assertTrue(savedReview.isPresent());
        assertThat(savedReview.get().getTenant()).isEqualTo(testUser);
        assertThat(savedReview.get().getProperty()).isEqualTo(testProperty);
    }

    @Test
    void shouldDeleteReview() {
        //Create a Review first
        var testUser = userRepository.findAll().get(0);
        var testProperty = propertyRepository.findAll().get(0);
        var reviewRequestDto = new ReviewRequestDto(testUser.getId(), testProperty.getId(), testReviewRatingDto);


        ReviewDto createdReview = reviewService.createReview(reviewRequestDto);
        var savedReview = reviewRepository.findById(createdReview.getId());
        assertTrue(savedReview.isPresent());

        //Deletion of this review
        var numberReviewsBeforeDeletion = reviewRepository.findAll().size();
        var numberReviewRatingsBeforeDeletion = reviewRatingRepository.findAll().size();

        reviewService.deleteReview(savedReview.get().getId());

        assertThrows(IllegalArgumentException.class, () -> {
            reviewService.getReviewById(savedReview.get().getId());
        });

        //Check that number of entries in Reviewrepository decreased by one and Reviewratingrepository by 4
        assertThat(reviewRepository.findAll().size()).isEqualTo(numberReviewsBeforeDeletion - 1);
        assertThat(reviewRatingRepository.findAll().size()).isEqualTo(numberReviewRatingsBeforeDeletion - 4);
    }
}
