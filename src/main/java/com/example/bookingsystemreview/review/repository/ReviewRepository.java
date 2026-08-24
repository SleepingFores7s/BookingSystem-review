package com.example.bookingsystemreview.review.repository;

import com.example.bookingsystemreview.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Review getReviewById(Long id);
}
