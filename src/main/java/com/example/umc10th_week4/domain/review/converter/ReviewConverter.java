package com.example.umc10th_week4.domain.review.converter;

import com.example.umc10th_week4.domain.mission.entity.UserMission;
import com.example.umc10th_week4.domain.review.dto.ReviewReqDTO;
import com.example.umc10th_week4.domain.review.dto.ReviewResDTO;
import com.example.umc10th_week4.domain.review.entity.Review;
import org.springframework.data.domain.Slice;

import java.util.List;

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

    // Review → 내 리뷰 한 건 DTO
    public static ReviewResDTO.MyReviewResponse toMyReviewResponse(Review review) {
        return ReviewResDTO.MyReviewResponse.builder()
                .reviewId(review.getId())
                .rating(review.getRating())
                .content(review.getContent())
                .build();
    }

    // Slice<Review> → 커서 기반 목록 DTO
    public static ReviewResDTO.MyReviewListResponse toMyReviewListResponse(
            Slice<Review> slice,
            String nextCursor
    ) {
        List<ReviewResDTO.MyReviewResponse> data = slice.getContent().stream()
                .map(ReviewConverter::toMyReviewResponse)
                .toList();

        return ReviewResDTO.MyReviewListResponse.builder()
                .data(data)
                .hasNext(slice.hasNext())
                .nextCursor(nextCursor)
                .pageSize(slice.getSize())
                .build();
    }
}