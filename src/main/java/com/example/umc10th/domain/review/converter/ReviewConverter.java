package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Reply;
import com.example.umc10th.domain.review.entity.Review;

import java.math.BigDecimal;

public class ReviewConverter {

    public static Reply toReply() {
        return Reply.builder()
                .content("")
                .build();
    }

    public static Review toReview(ReviewReqDTO.WriteReview dto, Member member, Store store, Reply reply) {
        return Review.builder()
                .content(dto.content())
                .star(BigDecimal.valueOf(dto.score()))
                .member(member)
                .store(store)
                .reply(reply)
                .build();
    }

    public static ReviewResDTO.WriteReview toWriteReview(Review review) {
        return ReviewResDTO.WriteReview.builder()
                .reviewId(review.getId())
                .memberId(review.getMember().getId())
                .storeId(review.getStore().getId())
                .score(review.getStar().floatValue())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
