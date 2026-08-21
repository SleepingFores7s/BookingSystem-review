package com.example.bookingsystemreview.review.controller;

import com.example.bookingsystemreview.review.dto.NewReviewDto;
import com.example.bookingsystemreview.review.dto.UpdateReviewDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReviewController {

    /*TODO C.R.U.D
    * 1. Get all reviews (admin)
    * 2. Get all reviews from 1 user (admin/user)
    * 3. Write a review to DB (user)
    * 4. Change a users review (user) (indicate that its been changed both in DB and to user)
    * 5. Remove a users review (admin (any)/user (their own)))
    *
    * */



    @GetMapping("/review")
    public ResponseEntity<?> getAllReviews() {

    }

    @PostMapping("/review")
    public ResponseEntity<?> createReview(@RequestBody NewReviewDto newReview) {

    }

    @GetMapping("/review/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable int id) {

    }

    @PutMapping("/review/{id}")
    public ResponseEntity<?> updateReviewById(@PathVariable int id, @RequestBody UpdateReviewDto updateReview) {

    }

    @DeleteMapping("/review/{id}")
    public ResponseEntity<?> deleteReviewById(@PathVariable int id) {

    }

}
