package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping
    public ApiResponse<ReviewResDTO.CreateReviewResultDTO> createReview(
            @RequestBody ReviewReqDTO.CreateReviewDTO request
    ) {
        ReviewResDTO.CreateReviewResultDTO result = reviewService.createReview(request);

        return ApiResponse.of(ReviewSuccessCode.REVIEW_CREATE_SUCCESS, result);
    }

    // 리뷰 답글 작성
    @PostMapping("/reply")
    public ApiResponse<ReviewResDTO.CreateReplyResultDTO> createReply(
            @RequestBody ReviewReqDTO.CreateReplyDTO request
    ) {
        ReviewResDTO.CreateReplyResultDTO result = reviewService.createReply(request);

        return ApiResponse.of(ReviewSuccessCode.REVIEW_CREATE_SUCCESS, result);
    }
}