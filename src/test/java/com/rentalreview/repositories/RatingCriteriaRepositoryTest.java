package com.rentalreview.repositories;

import com.rentalreview.config.TestBase;
import com.rentalreview.data.RatingCriteriaGenerator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class RatingCriteriaRepositoryTest extends TestBase {

    @Autowired
    RatingCriteriaRepository ratingCriteriaRepository;

    @AfterEach
    public void cleanUp(){
        ratingCriteriaRepository.deleteAll();
    }
    @Test
    void shouldCreateARatingCriteriaSuccessfully() {
        var ratingCriteria = RatingCriteriaGenerator.randomReviewCriteria();
        ratingCriteriaRepository.save(ratingCriteria);

        var ratingCriterias = ratingCriteriaRepository.findAll();

        assertThat(ratingCriterias).hasSize(1);
    }

}