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
    @GetMapping("/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getAllReviews() {

        List<ReviewResponseDto> reviews = reviewService.getAllReviews();

        if(reviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(reviews);

    }

    //GET USER REVIEWS
    @GetMapping("/reviews/user")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByUserId(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

    //CREATE REVIEW
    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponseDto> createNewReview(@AuthenticationPrincipal Long userId, @Valid @RequestBody NewReviewDto newReview) {
            ReviewResponseDto created = reviewService.createNewReview(userId, newReview);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //GET REVIEW USING REVIEW-ID
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable Long reviewId) {
            return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    //UPDATE REVIEW
    @PutMapping("/reviews")
    public ResponseEntity<ReviewResponseDto> updateReviewById(@AuthenticationPrincipal Long userId, @Valid @RequestBody UpdateReviewDto updateReview) {
            return ResponseEntity.ok(reviewService.updateReviewById(userId, updateReview));
    }

    //DELETE REVIEW
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<String> deleteReviewById(@AuthenticationPrincipal Long userId, @PathVariable Long reviewId) {
            reviewService.deleteReviewById(userId, reviewId);
            return ResponseEntity.ok("Review deleted successfully");
    }

}
