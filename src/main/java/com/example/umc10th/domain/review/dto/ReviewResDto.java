package com.example.umc10th.domain.review.dto;

import lombok.Builder;

import java.time.LocalDateTime;
public class ReviewResDto {

    @Builder
    public record CreateReview(
            Long reviewId,
            LocalDateTime createdAt
    )
    {}

    @Builder
    public record Review(
            Long reviewId,
            String name,
            Integer star_rating,
            String content,
            LocalDateTime createdAt
    ){}
}
