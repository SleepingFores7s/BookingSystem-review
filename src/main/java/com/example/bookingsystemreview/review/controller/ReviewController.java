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

    //TEST
    @GetMapping("/test")
    public String testMessage() {
        return "test";
    }

    //GET ALL REVIEWS
    @GetMapping("/reviews") //TODO SECURE METHOD
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    //GET USER REVIEWS
    @GetMapping("/reviews/user") //TODO SECURE METHOD
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByUserId(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

    //CREATE REVIEW
    @PostMapping("/reviews") //TODO SECURE METHOD
    public ResponseEntity<ReviewResponseDto> createNewReview(@AuthenticationPrincipal Long userId, @Valid @RequestBody NewReviewDto newReview) {
            ReviewResponseDto created = reviewService.createNewReview(userId, newReview);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //GET REVIEW USING REVIEW-ID
    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable("id") Long reviewId) {
            return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @GetMapping("/reviews/room/{id}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByRoomId(@PathVariable("id") Long roomId) {
        return ResponseEntity.ok(reviewService.getReviewsByRoomId(roomId));
    }

    //UPDATE REVIEW
    @PutMapping("/reviews") //TODO SECURE METHOD
    public ResponseEntity<ReviewResponseDto> updateReviewById(@AuthenticationPrincipal Long userId, @Valid @RequestBody UpdateReviewDto updateReview) {
            return ResponseEntity.ok(reviewService.updateReviewById(userId, updateReview));
    }

    @GetMapping("/reviews/room/avgRating/{id}")
    public ResponseEntity<Double> getAverageRatingByRoomId(@PathVariable("id") Long roomNumber) {
        return ResponseEntity.ok(reviewService.getAverageRatingByRoomNumber(roomNumber));
    }

    //DELETE REVIEW
    @DeleteMapping("/reviews/{id}") //TODO SECURE METHOD
    public ResponseEntity<?> deleteReviewById(@AuthenticationPrincipal Long userId, @PathVariable("id") Long reviewId) {
            return reviewService.deleteReviewById(userId, reviewId);
    }

}
