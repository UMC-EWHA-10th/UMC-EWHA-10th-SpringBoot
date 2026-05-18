package com.example.umc10th_week4.domain.review.controller;

import com.example.umc10th_week4.domain.review.dto.ReviewReqDTO;
import com.example.umc10th_week4.domain.review.dto.ReviewResDTO;
import com.example.umc10th_week4.domain.review.service.ReviewService;
import com.example.umc10th_week4.global.apiPayload.ApiResponse;
import com.example.umc10th_week4.global.apiPayload.code.GeneralSuccessCode;
import jakarta.validation.Valid;
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
            @RequestBody @Valid ReviewReqDTO.CreateReviewRequest request
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.SUCCESS, reviewService.createReview(request));
    }

    // 내 리뷰 목록 조회 (커서 기반)
    @GetMapping("/my")
    public ApiResponse<ReviewResDTO.MyReviewListResponse> getMyReviews(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "-1") String cursor,
            @RequestParam(defaultValue = "id") String query,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        return ApiResponse.onSuccess(GeneralSuccessCode.SUCCESS,
                reviewService.getMyReviews(userId, cursor, query, pageSize));
    }
}