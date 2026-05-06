package com.example.umc10th.domain.review.dto;

import lombok.Getter;

public class ReviewReqDTO {

    @Getter
    public static class CreateReviewDTO {
        private Long memberId;
        private Long storeId;
        private Float score;
        private String body;
    }

    @Getter
    public static class CreateReplyDTO {
        private Long reviewId;
        private String body;
    }
}