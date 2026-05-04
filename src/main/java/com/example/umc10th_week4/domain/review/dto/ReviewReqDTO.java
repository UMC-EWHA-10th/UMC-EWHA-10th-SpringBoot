package com.example.umc10th_week4.domain.review.dto;

import lombok.Getter;

public class ReviewReqDTO {

    @Getter
    public static class CreateReviewRequest {
        private Long userMissionId;  // 어떤 미션에 대한 리뷰인지
        private Integer rating;      // 별점 (1~5)
        private String content;      // 내용 (선택)
    }
}