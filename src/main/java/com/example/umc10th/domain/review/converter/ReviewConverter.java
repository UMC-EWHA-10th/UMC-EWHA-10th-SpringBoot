package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.entity.Reply;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.domain.review.enums.ReviewSortType;

import java.math.BigDecimal;
import java.util.List;

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

    // 내 리뷰 목록 (커서 페이징, 사진 제외)
    public static ReviewResDTO.MyReviewList toMyReviewList(
            List<Review> reviews,
            int pageSize,
            ReviewSortType sortType
    ) {
        boolean hasNext = reviews.size() > pageSize;
        List<Review> page = hasNext ? reviews.subList(0, pageSize) : reviews;

        List<ReviewResDTO.MyReviewPreview> previews = page.stream()
                .map(r -> ReviewResDTO.MyReviewPreview.builder()
                        .reviewId(r.getId())
                        .storeId(r.getStore().getId())
                        .storeName(r.getStore().getName())
                        .score(r.getStar().floatValue())
                        .content(r.getContent())
                        .createdAt(r.getCreatedAt())
                        .build())
                .toList();

        Review last = page.isEmpty() ? null : page.get(page.size() - 1);
        Float nextStar = (sortType == ReviewSortType.STAR && last != null)
                ? last.getStar().floatValue()
                : null;

        return ReviewResDTO.MyReviewList.builder()
                .reviews(previews)
                .sort(sortType.name())
                .nextCursorStar(nextStar)
                .nextCursorId(last != null ? last.getId() : null)
                .hasNext(hasNext)
                .build();
    }
}
