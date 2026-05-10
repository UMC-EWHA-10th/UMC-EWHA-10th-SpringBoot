package com.example.umc10th.domain.review.dto;

import com.example.umc10th.domain.review.enums.ReviewSortType;

import java.util.List;

public class ReviewReqDto {
    public record CreateReview(
            Integer starRating,
            String content,
            List<String> reviewImageUrls
    ){}

    public record GetReview (
            Long lastId,
            Integer lastStarRating,
            Integer size,
            ReviewSortType sortType//ID순, 별점순
    ){ }
}
