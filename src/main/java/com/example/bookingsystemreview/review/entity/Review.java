package com.example.bookingsystemreview.review.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Integer roomNumber;

    @Column(nullable = false)
    private String reviewContent;

    @Column(nullable = false)
    private Integer reviewScore;

    @CreationTimestamp
    @Column(name = "creation_date", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;

    public Review() {}

    public Review(Long userId,
                  int roomNumber,
                  String reviewContent,
                  Integer reviewScore) {
        this.userId = userId;
        this.roomNumber = roomNumber;
        this.reviewContent = reviewContent;
        this.reviewScore = reviewScore;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getReviewContent() {
        return reviewContent;
    }
    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Integer getReviewScore() {
        return reviewScore;
    }
    public void setReviewScore(Integer reviewScore) {
        this.reviewScore = reviewScore;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime reviewDate) {
        this.creationDate = reviewDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
