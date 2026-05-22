package com.example.umc10th_week4.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class ReviewResDTO {

    @Getter
    @Builder
    public static class CreateReviewResponse {
        private Long reviewId;
        private Integer rating;
        private String content;
    }

    // 내 리뷰 한 건
    @Getter
    @Builder
    public static class MyReviewResponse {
        private Long reviewId;
        private Integer rating;
        private String content;
    }

    // 커서 기반 목록 응답
    @Getter
    @Builder
    public static class MyReviewListResponse {
        private List<MyReviewResponse> data;
        private Boolean hasNext;
        private String nextCursor;
        private Integer pageSize;
    }

}