package com.rentalreview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewRatingDto {
    private Long criteriaId;
    private Integer score;
    private String comment;
}
