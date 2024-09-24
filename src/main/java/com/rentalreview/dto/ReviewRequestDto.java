package com.rentalreview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReviewRequestDto {
    private Long propertyID;
    private Long userID;
    private List<ReviewRatingDto> ratings;


}
