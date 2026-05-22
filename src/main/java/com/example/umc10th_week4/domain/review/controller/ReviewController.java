package com.example.umc10th_week4.domain.review.controller;

import com.example.umc10th_week4.domain.review.dto.ReviewReqDTO;
import com.example.umc10th_week4.domain.review.dto.ReviewResDTO;
import com.example.umc10th_week4.domain.review.service.ReviewService;
import com.example.umc10th_week4.global.apiPayload.ApiResponse;
import com.example.umc10th_week4.global.apiPayload.code.GeneralSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping
    public ApiResponse<ReviewResDTO.CreateReviewResponse> createReview(
            @RequestBody ReviewReqDTO.CreateReviewRequest request
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.SUCCESS, reviewService.createReview(request));
    }
}