package com.example.bookingsystemreview.review.controller;

import com.example.bookingsystemreview.review.dto.NewReviewDto;
import com.example.bookingsystemreview.review.dto.ReviewResponseDto;
import com.example.bookingsystemreview.review.dto.UpdateReviewDto;
import com.example.bookingsystemreview.review.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/reviews/user/{userId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

    //CREATE REVIEW
    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponseDto> createNewReview(@Valid @RequestBody NewReviewDto newReview) {
            ReviewResponseDto created = reviewService.createNewReview(newReview);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //GET REVIEW USING ID
    @GetMapping("/reviews/{id}")
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable Long id) {
            return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    //UPDATE REVIEW
    @PutMapping("/reviews/{id}")
    public ResponseEntity<ReviewResponseDto> updateReviewById(@PathVariable Long id, @Valid @RequestBody UpdateReviewDto updateReview) {
            return ResponseEntity.ok(reviewService.updateReviewById(id, updateReview));
    }

    //DELETE REVIEW
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<Void> deleteReviewById(@PathVariable Long id) {
            reviewService.deleteReviewById(id);
            return ResponseEntity.noContent().build();
    }

}
