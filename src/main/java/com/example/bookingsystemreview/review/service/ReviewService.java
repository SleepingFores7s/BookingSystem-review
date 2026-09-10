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
import org.springframework.security.access.AccessDeniedException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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
                        review.getUserId(),
                        review.getRoomNumber(),
                        review.getReviewContent(),
                        review.getReviewScore(),
                        review.getCreationDate(),
                        review.getUpdateDate()
                ))
                .toList();
    }

    public List<ReviewResponseDto> getReviewsByUserId(Long userId) {
        return reviewRepository.findAllByUserId(userId);
    }

    public ReviewResponseDto createNewReview(Long userId, NewReviewDto dto) {

        String cleanContent = sanitizer.sanitize(dto.reviewContent());

        Review review = reviewRepository.save(new Review(
                userId,
                dto.roomNumber(),
                cleanContent,
                dto.reviewScore())
        );

        return new ReviewResponseDto(
                review.getId(),
                review.getUserId(),
                review.getRoomNumber(),
                review.getReviewContent(),
                review.getReviewScore(),
                review.getCreationDate(),
                review.getUpdateDate()
        );
    }

    public ReviewResponseDto getReviewByReviewId(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No review found with id: " + id));

        return new ReviewResponseDto(
                review.getId(),
                review.getUserId(),
                review.getRoomNumber(),
                review.getReviewContent(),
                review.getReviewScore(),
                review.getCreationDate(),
                review.getUpdateDate()
        );
    }

    public List<ReviewResponseDto> getReviewsByRoomNumber(Integer roomNumber) {
        return reviewRepository.findAllByRoomNumber(roomNumber);
    }

    public ReviewResponseDto updateReviewByReviewId(Long userId, UpdateReviewDto dto) {

        Review review = reviewRepository.findById(dto.reviewId())
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with id: " + dto.reviewId()));

        if (!review.getUserId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to update this review");
        }

        String cleanContent = sanitizer.sanitize(dto.reviewContent());

        review.setReviewContent(cleanContent);
        review.setReviewScore(dto.reviewScore());
        Review savedReview = reviewRepository.save(review);

        return new ReviewResponseDto(
                savedReview.getId(),
                savedReview.getUserId(),
                savedReview.getRoomNumber(),
                savedReview.getReviewContent(),
                savedReview.getReviewScore(),
                savedReview.getCreationDate(),
                savedReview.getUpdateDate()
        );
    }

    public Double getAverageRatingByRoomNumber(Integer roomNumber) {
        return reviewRepository.findAverageRatingByRoomNumber(roomNumber);
    }

    public ResponseEntity<?> deleteReviewById(Long userId, Long reviewId) {

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->  new ResourceNotFoundException("Review not found with id: " + reviewId));

        if(!review.getUserId().equals(userId)) {
            throw new AccessDeniedException("You don't have permission to delete this review");
        }

        reviewRepository.delete(review);
        return ResponseEntity
                .ok()
                .body("Deleted review with id: " + reviewId);
    }

    public List<ReviewResponseDto> getReviewsByRoomId(Long roomId) {

        if(roomId == null) {
            throw new MissingValueException("room id is null");
        }

        List<ReviewResponseDto> reviews = reviewRepository.findAllByRoomId(roomId);

        if (reviews.isEmpty()) {
            throw new ResourceNotFoundException("No reviews found for room ID: " + roomId);
        }

        return reviews;
    }

    public Double getAverageRatingByRoomNumber(Long roomNumber) {
        return reviewRepository.findAverageRatingByRoomId(roomNumber);
    }
}
