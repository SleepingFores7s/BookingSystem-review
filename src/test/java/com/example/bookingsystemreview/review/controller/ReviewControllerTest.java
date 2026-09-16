package com.example.bookingsystemreview.review.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewControllerTest {

    private ReviewController reviewController;

    @Test
    void getAllReviews_ShouldReturnOk() {

        assertEquals(HttpStatus.OK, reviewController.getAllReviews().getStatusCode());

    }
}