package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.review.dto.ReviewReqDto;
import com.example.umc10th.domain.review.dto.ReviewResDto;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.global.PageResDto;

public class ReviewConverter {
    //ReviewReqDto -> 엔티티
    public static Review toReview(ReviewReqDto.CreateReview request, Member member, Store store) {
        return Review.builder()
                .starRating(request.starRating())
                .content(request.content())
                .member(member)
                .store(store)
                .build();
    }

    //엔티티 -> ReviewReqDto
    public static ReviewResDto.CreateReview toReviewResDto(Review review){
        return ReviewResDto.CreateReview.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();
    }

}
