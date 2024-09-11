package com.rentalreview.data;

import com.rentalreview.entities.Property;
import com.rentalreview.entities.Review;
import com.rentalreview.entities.User;

import java.time.LocalDateTime;

public class ReviewGenerator {
    public static Review randomReview(User user, Property property) {
        return Review.builder()
                .tenant(user)
                .createdTimestamp(LocalDateTime.now())
                .property(property)
                .build();
    }
}
