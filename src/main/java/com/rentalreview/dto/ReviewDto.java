package com.rentalreview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReviewDto {
    private long id;
    private UserDto tenant;
    private PropertyDto property;
    private List<ReviewRatingDto> reviewRatings;
    private String createdAt;
}
