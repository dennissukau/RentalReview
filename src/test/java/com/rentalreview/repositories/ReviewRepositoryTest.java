package com.rentalreview.repositories;
import com.rentalreview.config.TestBase;
import com.rentalreview.data.PropertyGenerator;
import com.rentalreview.data.ReviewGenerator;
import com.rentalreview.data.UserGenerator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class ReviewRepositoryTest extends TestBase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private PropertyRepository propertyRepository;

   @AfterEach
   public void cleanUp(){
       reviewRepository.deleteAll();
       propertyRepository.deleteAll();
       userRepository.deleteAll();
   }
    @Test
    void shouldCreateAReviewSuccessfully() {
        //Create user and property

        var user = UserGenerator.randomUser();
        var property = PropertyGenerator.randomProperty(user);

        //Save in repository
        userRepository.save(user);
        propertyRepository.save(property);

        //Create a review with the user and property entities created above
        var review = ReviewGenerator.randomReview(user, property);

        reviewRepository.save(review);
        var reviews = reviewRepository.findAll();

        //Test
        assertThat(reviews).hasSize(1);

    }

}