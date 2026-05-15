package com.example.chap4.dto;

import lombok.Builder;

import java.util.List;

public class ReviewResponseDTO {

    @Builder
    public record MyReviewResponse(
            Long reviewId,
            Integer rating,
            String content
    ) {
    }

    @Builder
    public record MyReviewCursorResponse(
            List<MyReviewResponse> data,
            Long nextCursor,
            Boolean hasNext
    ) {
    }
}