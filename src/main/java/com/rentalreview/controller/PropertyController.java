package com.rentalreview.controller;

import com.rentalreview.dto.ReviewDto;
import com.rentalreview.entities.Review;
import com.rentalreview.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
@RequiredArgsConstructor
public class PropertyController {

    public final ReviewService reviewService;

    @GetMapping("/property/{propertyId}/reviews")
    public ResponseEntity<List<ReviewDto>> getReviewsByProperty(@PathVariable Long propertyId) {
        List<ReviewDto> reviews = reviewService.getReviewsByProperty(propertyId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }
}
