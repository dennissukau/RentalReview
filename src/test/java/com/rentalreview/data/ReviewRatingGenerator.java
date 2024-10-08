package com.rentalreview.data;

import com.rentalreview.entities.Review;
import com.rentalreview.entities.ReviewRating;
import com.rentalreview.entities.RatingCriteria;
import com.rentalreview.entities.ReviewRatingId;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ReviewRatingGenerator {

    public static ReviewRating randomReviewRating(Review review, RatingCriteria ratingCriteria) {
        var random = new Random();
        var reviewRatingId = new ReviewRatingId(review.getId(), ratingCriteria.getId());

        return ReviewRating.builder()
                .reviewRatingId(reviewRatingId)
                .comment(randomAlphanumeric(10))
                .rating(random.nextInt(11))
                .build();
    }

}
