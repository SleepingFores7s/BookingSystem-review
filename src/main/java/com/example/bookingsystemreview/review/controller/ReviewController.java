package com.example.bookingsystemreview.review.controller;

import com.example.bookingsystemreview.review.dto.NewReviewDto;
import com.example.bookingsystemreview.review.dto.ReviewResponseDto;
import com.example.bookingsystemreview.review.dto.UpdateReviewDto;
import com.example.bookingsystemreview.review.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //GET ALL REVIEWS
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    //GET USER REVIEWS
    @GetMapping("/reviews/user")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByUserId(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

    //CREATE REVIEW
    @PostMapping("/reviews") //TODO SECURE METHOD
    public ResponseEntity<ReviewResponseDto> createNewReview(@AuthenticationPrincipal Long userId, @Valid @RequestBody NewReviewDto newReview) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.createNewReview(userId, newReview));
    }

    //GET REVIEW USING REVIEW-ID
    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewResponseDto> getReviewByReviewId(@PathVariable("id") Long reviewId) {
        return ResponseEntity.ok(reviewService.getReviewByReviewId(reviewId));
    }

    @GetMapping("/reviews/room/{roomNumber}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByRoomNumber(@PathVariable("roomNumber") Integer roomNumber) {
        return ResponseEntity.ok(reviewService.getReviewsByRoomNumber(roomNumber));
    }

    //UPDATE REVIEW
    @PutMapping("/reviews") //TODO SECURE METHOD
    public ResponseEntity<ReviewResponseDto> updateReviewByReviewId(@AuthenticationPrincipal Long userId, @Valid @RequestBody UpdateReviewDto updateReview) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(reviewService.updateReviewByReviewId(userId, updateReview));
    }

    @GetMapping("/reviews/room/avgRating/{roomNumber}")
    public ResponseEntity<Double> getAverageRatingByRoomNumber(@PathVariable("roomNumber") Integer roomNumber) {
        return ResponseEntity.ok(reviewService.getAverageRatingByRoomNumber(roomNumber));
    }

    //DELETE REVIEW
    @DeleteMapping("/reviews/{reviewId}") //TODO SECURE METHOD
    public ResponseEntity<?> deleteReviewById(@AuthenticationPrincipal Long userId, @PathVariable("reviewId") Long reviewId) {
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(reviewService.deleteReviewById(userId, reviewId));
    }

}
