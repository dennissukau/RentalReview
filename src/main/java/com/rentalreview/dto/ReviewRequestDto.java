package com.rentalreview.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewRequestDto {
    private Long propertyID;
    private Long userID;
    private List<ReviewRatingDto> ratings;
}
