package com.example.umc10th.domain.review.converter;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.mission.entity.Store;
import com.example.umc10th.domain.review.dto.ReviewReqDto;
import com.example.umc10th.domain.review.dto.ReviewResDto;
import com.example.umc10th.domain.review.entity.Review;
import com.example.umc10th.global.PageResDto;
import org.springframework.data.domain.Page;

import java.util.List;

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

    //엔티티 -> ReviewResDto.CreateReview
    public static ReviewResDto.CreateReview toCreateReviewDto(Review review){
        return ReviewResDto.CreateReview.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();
    }

    //엔티티 -> ReviewResDto.Review
    public static ReviewResDto.Review toReviewDto(Review review){
        return ReviewResDto.Review.builder()
                .reviewId(review.getId())
                .name(review.getMember().getName())
                .star_rating(review.getStarRating())
                .content(review.getContent())
                .createdAt(review.getCreatedAt())
                .build();
    }

    //Page -> PageResDto
    public static PageResDto<ReviewResDto.Review> toPageReviewDto(Page<Review> reviewPage){
        //엔티티 리스트 -> Dto 리스트
        List<ReviewResDto.Review> reviewList=reviewPage.getContent().stream()
                .map(ReviewConverter::toReviewDto)
                .toList();

        //마지막 요소 가져오기(nextCursor)
        ReviewResDto.Review lastReview=reviewList.isEmpty()?null:reviewList.getLast();

        //Dto 리스트 -> PageResDto
        return PageResDto.<ReviewResDto.Review>builder()
                .dataList(reviewList)
                .listSize(reviewList.size())
                .isFirst(reviewPage.isFirst())
                .isLast(reviewPage.isLast())
                .nextCursor(lastReview!=null?lastReview.reviewId():null)
                .nextSubCursor(lastReview!=null?lastReview.star_rating():null)
                .build();
    }


}
