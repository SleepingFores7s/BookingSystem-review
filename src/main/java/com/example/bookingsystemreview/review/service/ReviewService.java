package com.example.bookingsystemreview.review.service;

import com.example.bookingsystemreview.review.dto.NewReviewDto;
import com.example.bookingsystemreview.review.dto.ReviewResponseDto;
import com.example.bookingsystemreview.review.entity.Review;
import com.example.bookingsystemreview.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.getAllReviews();
    }

    public ReviewResponseDto createNewReview(NewReviewDto dto) {

        try {
            Review review = reviewRepository.save(new Review(
                    dto.userId(),
                    dto.roomId(),
                    dto.reviewContent(),
                    dto.reviewScore()));

            return new ReviewResponseDto(
                    review.getId(),
                    review.getReviewContent(),
                    review.getReviewScore(),
                    review.getCreationDate(),
                    review.getUpdateDate()
            );
        }catch (Exception e){
            return null;
        }
    }

    public ReviewResponseDto getReviewById(Long id) {
        try{

            Review review = reviewRepository.getReviewById(id);

            if (review == null) {
                return null;
            }

            return new ReviewResponseDto(
                    review.getId(),
                    review.getReviewContent(),
                    review.getReviewScore(),
                    review.getCreationDate(),
                    review.getUpdateDate()
            );

        }catch (Exception e){
            return null;
        }
    }



}
