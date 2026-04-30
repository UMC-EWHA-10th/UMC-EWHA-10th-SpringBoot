package com.example.umc10th.domain.review.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class ReviewResDTO {

    // 리뷰 작성 결과
    @Builder
    public record WriteReview(
            Long reviewId,
            Long memberId,
            Long storeId,
            Float score,
            String content,
            LocalDateTime createdAt
    ) {}
}
