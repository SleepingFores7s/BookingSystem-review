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
import org.springframework.http.HttpStatus;
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

    public ReviewResponseDto getReviewById(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Object not found with id: " + id));

        return new ReviewResponseDto(
                review.getId(),
                review.getUserId(),
                review.getRoomId(),
                review.getReviewContent(),
                review.getReviewScore(),
                review.getCreationDate(),
                review.getUpdateDate()
        );
    }

    public ReviewResponseDto updateReviewById(Long userId, UpdateReviewDto dto) {
        Review review = reviewRepository.findById(dto.reviewId())
                .orElseThrow(() -> new ResourceNotFoundException("Object not found with id: " + dto.reviewId()));

        if (!review.getUserId().equals(userId)) {
            throw new MismatchedUserIdException("User does not have permission to update review with id: " + dto.reviewId());
        }

        String cleanContent = sanitizer.sanitize(dto.reviewContent());

        review.setReviewContent(cleanContent);
        review.setReviewScore(dto.reviewScore());
        Review savedReview = reviewRepository.save(review);

        return new ReviewResponseDto(
                review.getId(),
                review.getUserId(),
                review.getRoomId(),
                review.getReviewContent(),
                review.getReviewScore(),
                review.getCreationDate(),
                review.getUpdateDate()
        );
    }

    public ResponseEntity<?> deleteReviewById(Long userId, Long reviewId) {

        if(reviewId == null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MissingValueException("reviewId is null"));
        }

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->  new ResourceNotFoundException("Object not found with id: " + reviewId));

        if(!review.getUserId().equals(userId)) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body("You don't have permission to delete this review");
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
