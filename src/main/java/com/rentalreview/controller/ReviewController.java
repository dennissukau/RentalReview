package com.rentalreview.controller;


import com.rentalreview.dto.ReviewRequestDto;
import com.rentalreview.dto.UserDto;
import com.rentalreview.entities.Review;
import com.rentalreview.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.internalServerError;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Object> postReview(@RequestBody ReviewRequestDto reviewRequest) {
        Review review;
        try {
            review = reviewService.createReview(reviewRequest);

            return ResponseEntity.ok(review);
        } catch (DuplicateKeyException e) {
            return badRequest().build();
        } catch (Exception e) {
            return internalServerError().build();
        }
    }

}


