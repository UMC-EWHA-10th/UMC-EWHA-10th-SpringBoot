package com.example.umc10th.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

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

    @Getter
    @Builder
    public static class MyReviewCursorResultDTO {
        private List<MyReviewDTO> reviewList;
        private Integer listSize;
        private Boolean hasNext;
        private String nextCursor;
    }

    @Getter
    @Builder
    public static class MyReviewDTO {
        private Long reviewId;
        private String storeName;
        private Float score;
        private String body;
    }
}