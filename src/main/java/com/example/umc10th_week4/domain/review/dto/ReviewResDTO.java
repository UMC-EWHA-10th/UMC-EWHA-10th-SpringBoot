package com.example.umc10th_week4.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

public class ReviewResDTO {

    @Getter
    @Builder
    public static class CreateReviewResponse {
        private Long reviewId;
        private Integer rating;
        private String content;
    }
}