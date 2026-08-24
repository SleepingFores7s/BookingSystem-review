package com.example.bookingsystemreview.review.repository;

import com.example.bookingsystemreview.review.dto.ReviewResponseDto;
import com.example.bookingsystemreview.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> getAllReviews();

    Review getReviewById(Long id);

    Review createReview();
}
