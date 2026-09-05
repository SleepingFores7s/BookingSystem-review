package com.example.bookingsystemreview.review.service;

import com.example.bookingsystemreview.exceptionhandler.customexceptions.MismatchedUserIdException;
import com.example.bookingsystemreview.exceptionhandler.customexceptions.MissingValueException;
import com.example.bookingsystemreview.exceptionhandler.customexceptions.ResourceNotFoundException;
import com.example.bookingsystemreview.review.dto.NewReviewDto;
import com.example.bookingsystemreview.review.dto.ReviewResponseDto;
import com.example.bookingsystemreview.review.dto.UpdateReviewDto;
import com.example.bookingsystemreview.review.entity.Review;
import com.example.bookingsystemreview.review.repository.ReviewRepository;
import com.example.bookingsystemreview.security.sanitation.HtmlSanitizerUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final HtmlSanitizerUtil sanitizer;

    public ReviewService(ReviewRepository reviewRepository, HtmlSanitizerUtil sanitizer) {
        this.reviewRepository = reviewRepository;
        this.sanitizer = sanitizer;
    }

    public List<ReviewResponseDto> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(review -> new ReviewResponseDto(
                        review.getId(),
                        review.getReviewContent(),
                        review.getReviewScore(),
                        review.getCreationDate(),
                        review.getUpdateDate()
                ))
                .toList();
    }

    public List<ReviewResponseDto> getReviewsByUserId(Long userId) {
        if(userId == null) {
            throw new MissingValueException("userId is null");
        }

        List<ReviewResponseDto> reviews = reviewRepository.findAllByUserId(userId);

        if (reviews.isEmpty()) {
            throw new ResourceNotFoundException("No reviews found for user ID: " + userId);
        }

        return reviews;
    }

    public ReviewResponseDto createNewReview(Long userId, NewReviewDto dto) {

        String cleanContent = sanitizer.sanitize(dto.reviewContent());

        Review review = reviewRepository.save(new Review(
                userId,
                dto.roomId(),
                cleanContent,
                dto.reviewScore())
        );

        return new ReviewResponseDto(
                review.getId(),
                review.getReviewContent(),
                review.getReviewScore(),
                review.getCreationDate(),
                review.getUpdateDate()
        );
    }

    public ReviewResponseDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Object not found with id: " + id));

        return new ReviewResponseDto(
                review.getId(),
                review.getReviewContent(),
                review.getReviewScore(),
                review.getCreationDate(),
                review.getUpdateDate()
        );
    }

    public ReviewResponseDto updateReviewById(Long userId, UpdateReviewDto dto) {
        Review review = reviewRepository.findById(dto.reviewId())
                .orElseThrow(() -> new ResourceNotFoundException("Object not found with id: " + dto.reviewId()));

        if (!Objects.equals(review.getUserId(), userId)) {
            throw new MismatchedUserIdException("id: " +dto.reviewId()+ " did not match review id.");
        }

        String cleanContent = sanitizer.sanitize(dto.reviewContent());

        review.setReviewContent(cleanContent);
        review.setReviewScore(dto.reviewScore());
        Review savedReview = reviewRepository.save(review);

        return new ReviewResponseDto(
                savedReview.getId(),
                savedReview.getReviewContent(),
                savedReview.getReviewScore(),
                savedReview.getCreationDate(),
                savedReview.getUpdateDate()
        );
    }

    public void deleteReviewById(Long userId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Review not found with id: " + reviewId)
                );

        if(!review.getUserId().equals(userId)) {
            throw new MismatchedUserIdException("You dont have permission to delete this review");
        }

        reviewRepository.deleteById(reviewId);
    }


}
