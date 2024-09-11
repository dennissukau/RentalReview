package com.rentalreview.data;

import com.rentalreview.entities.RatingCriteria;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class RatingCriteriaGenerator {

    public static RatingCriteria randomReviewCriteria() {
        return RatingCriteria.builder()
                .name(randomAlphanumeric(6))
                .build();
    }
}
