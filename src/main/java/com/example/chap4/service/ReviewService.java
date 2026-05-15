package com.example.chap4.service;

import com.example.chap4.domain.Review;
import com.example.chap4.domain.repository.ReviewRepository;
import com.example.chap4.dto.ReviewRequestDTO;
import com.example.chap4.dto.ReviewResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewResponseDTO.MyReviewCursorResponse getMyReviews(
            ReviewRequestDTO.MyReviewRequest request
    ) {
        Long cursor = request.cursor() == null ? Long.MAX_VALUE : request.cursor();

        PageRequest pageRequest = PageRequest.of(0, request.size());

        Page<Review> reviewPage;

        if ("rating".equals(request.sortBy())) {
            reviewPage = reviewRepository.findByMember_IdAndIdLessThanOrderByRatingDescIdDesc(
                    request.memberId(),
                    cursor,
                    pageRequest
            );
        } else {
            reviewPage = reviewRepository.findByMember_IdAndIdLessThanOrderByIdDesc(
                    request.memberId(),
                    cursor,
                    pageRequest
            );
        }

        List<ReviewResponseDTO.MyReviewResponse> reviewList =
                reviewPage.getContent()
                        .stream()
                        .map(review -> ReviewResponseDTO.MyReviewResponse.builder()
                                .reviewId(review.getId())
                                .rating(review.getRating())
                                .content(review.getContent())
                                .build())
                        .toList();

        Long nextCursor = reviewList.isEmpty()
                ? null
                : reviewList.get(reviewList.size() - 1).reviewId();

        return ReviewResponseDTO.MyReviewCursorResponse.builder()
                .data(reviewList)
                .nextCursor(nextCursor)
                .hasNext(reviewPage.hasNext())
                .build();
    }
}
