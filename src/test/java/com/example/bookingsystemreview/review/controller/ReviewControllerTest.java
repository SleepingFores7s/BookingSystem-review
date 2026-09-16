package com.example.bookingsystemreview.review.controller;

import com.example.bookingsystemreview.review.entity.Review;
import com.example.bookingsystemreview.review.repository.ReviewRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;


import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewRepository reviewRepository;

    @BeforeEach
    void setUp() {
        reviewRepository.deleteAll();
        reviewRepository.save(new Review(1L, 102, "Test 1 - User 1", 4));
    }

    @Test
    void getAllReviews_ShouldReturnOk() throws Exception {
        mockMvc.perform(get("/reviews"))
                .andExpect(status().isOk());
    }

    @Test
    void getReviewsByRoomNumber_ShouldReturnReviews() throws Exception {
        mockMvc.perform(get("/reviews/room/102"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roomNumber").value(102));
    }
}