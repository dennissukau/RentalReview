package com.rentalreview.controller;


import com.rentalreview.dto.ReviewDto;
import com.rentalreview.dto.ReviewRequestDto;
import com.rentalreview.mapper.ReviewMapper;
import com.rentalreview.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import static org.springframework.http.ResponseEntity.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @PostMapping
    public ResponseEntity<ReviewDto> postReview(@RequestBody ReviewRequestDto reviewRequest) {
        ReviewDto review;
        try {
            review = reviewService.createReview(reviewRequest);
            return ResponseEntity.ok(review);
        } catch (DuplicateKeyException e) {
            return badRequest().build();
        } catch (Exception e) {
            return internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) {
        try {
            ReviewDto review = reviewService.getReviewById(id);
            return ResponseEntity.ok(review);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}



