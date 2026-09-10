package com.example.bookingsystemreview.review.repository;

import com.example.bookingsystemreview.review.dto.ReviewResponseDto;
import com.example.bookingsystemreview.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<ReviewResponseDto> findAllByUserId(Long userId);
    List<ReviewResponseDto> findAllByRoomNumber(Integer roomNumber);

    @Query("SELECT AVG(r.reviewScore) FROM Review r WHERE r.roomNumber = :roomNumber")
    Double findAverageRatingByRoomNumber(Integer roomNumber);
}
