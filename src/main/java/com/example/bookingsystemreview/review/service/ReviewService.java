package com.example.bookingsystemreview.review.service;

import com.example.bookingsystemreview.review.dto.NewReviewDto;
import com.example.bookingsystemreview.review.dto.ReviewResponseDto;
import com.example.bookingsystemreview.review.dto.UpdateReviewDto;
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
        return reviewRepository.findAll();
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
            return null; //todo Change catch to return a custom exception
        }
    }

    public ReviewResponseDto getReviewById(Long id) {
        //todo Change to return custom exceptions
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

    public ReviewResponseDto updateReviewById(Long id, UpdateReviewDto dto) {
        //todo Change to return custom exceptions
        try {
            Review review = reviewRepository.getReviewById(id);
            if (review == null) {
                return null;
            }
            review.setReviewContent(dto.reviewContent());
            review.setReviewScore(dto.reviewScore());

            reviewRepository.save(review);

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

    public boolean deleteReviewById(Long id) {
        //todo Change to return custom exceptions
        try{

            reviewRepository.deleteById(id);
            return true;

        }catch (Exception e){
            return false;
        }
    }



}
