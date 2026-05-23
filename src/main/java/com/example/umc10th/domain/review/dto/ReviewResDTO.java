package com.example.umc10th.domain.review.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

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

    // 내 리뷰 1건 (사진 제외)
    @Builder
    public record MyReviewPreview(
            Long reviewId,
            Long storeId,
            String storeName,
            Float score,
            String content,
            LocalDateTime createdAt
    ) {}

    // 내 리뷰 목록 응답 (커서 페이징)
    // sort=STAR 일 때만 nextCursorStar 사용, sort=ID 일 때는 null
    @Builder
    public record MyReviewList(
            List<MyReviewPreview> reviews,
            String sort,                // "ID" | "STAR"
            Float nextCursorStar,
            Long nextCursorId,
            boolean hasNext
    ) {}
}
