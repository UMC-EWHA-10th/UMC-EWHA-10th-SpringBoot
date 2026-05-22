package com.example.umc10th_week4.domain.review.converter;

import com.example.umc10th_week4.domain.mission.entity.UserMission;
import com.example.umc10th_week4.domain.review.dto.ReviewReqDTO;
import com.example.umc10th_week4.domain.review.dto.ReviewResDTO;
import com.example.umc10th_week4.domain.review.entity.Review;

public class ReviewConverter {

    // Request → Entity
    public static Review toReview(ReviewReqDTO.CreateReviewRequest request, UserMission userMission) {
        return Review.builder()
                .userMission(userMission)
                .rating(request.getRating())
                .content(request.getContent())
                .build();
    }

    // Entity → Response
    public static ReviewResDTO.CreateReviewResponse toCreateReviewResponse(Review review) {
        return ReviewResDTO.CreateReviewResponse.builder()
                .reviewId(review.getId())
                .rating(review.getRating())
                .content(review.getContent())
                .build();
    }
}