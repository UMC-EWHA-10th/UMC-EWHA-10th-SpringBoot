package com.example.umc10th.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

public class ReviewResDTO {

    @Getter
    @Builder
    public static class CreateReviewResultDTO {
        private Long reviewId;
        private Float score;
        private String body;
    }

    @Getter
    @Builder
    public static class CreateReplyResultDTO {
        private Long replyId;
        private Long reviewId;
        private String body;
    }
}