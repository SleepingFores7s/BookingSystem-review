package com.example.bookingsystemreview.review.controller;

import com.example.bookingsystemreview.review.dto.NewReviewDto;
import com.example.bookingsystemreview.review.dto.ReviewResponseDto;
import com.example.bookingsystemreview.review.dto.UpdateReviewDto;
import com.example.bookingsystemreview.review.entity.Review;
import com.example.bookingsystemreview.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReviewController {

    /*TODO C.R.U.D
    * 1. OK Get all reviews (admin)
    * 2. OK Get all reviews from 1 user (admin/user)
    * 3. OK Write a review to DB (user)
    * 4. OK Change a users review (user) (indicate that its been changed both in DB and to user)
    * 5. OK Remove a users review (admin (any)/user (their own)))
    *
    * */

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/test")
    public String testMessage() {
        return "test";
    }

    @GetMapping("/review")
    public ResponseEntity<?> getAllReviews() {

        List<Review> reviews = reviewService.getAllReviews();

        if(reviews.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(reviewService.getAllReviews());

    }

    @PostMapping("/review")
    public ResponseEntity<?> createReview(@RequestBody NewReviewDto newReview) {

        ReviewResponseDto dto = reviewService.createNewReview(newReview);
        if(dto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(dto);

    }

    @GetMapping("/review/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable Long id) {

        try{

            return ResponseEntity.ok(reviewService.getReviewById(id));

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @PutMapping("/review/{id}")
    public ResponseEntity<?> updateReviewById(@PathVariable Long id, @RequestBody UpdateReviewDto updateReview) {

        try {
            return ResponseEntity.ok(reviewService.updateReviewById(id, updateReview));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity<?> deleteReviewById(@PathVariable Long id) {
        try{
            boolean deleted = reviewService.deleteReviewById(id);
            return ResponseEntity.ok().body(deleted);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
