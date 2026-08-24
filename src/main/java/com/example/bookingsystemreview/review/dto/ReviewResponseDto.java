package com.example.bookingsystemreview.review.dto;

import java.time.LocalDateTime;

public record ReviewResponseDto(
        Long id,
        String reviewContent,
        Integer reviewScore,
        LocalDateTime creationDate,
        LocalDateTime updateDate
) {
}
