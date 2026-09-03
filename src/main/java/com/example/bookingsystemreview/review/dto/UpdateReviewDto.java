package com.example.bookingsystemreview.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateReviewDto(
        @NotNull
        Long reviewId,
        @NotNull
        String reviewContent,
        @NotNull
        @Min(1)
        @Max(5)
        Integer reviewScore
) {
}
