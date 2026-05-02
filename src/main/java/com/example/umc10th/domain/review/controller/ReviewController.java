package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.review.dto.ReviewReqDto;
import com.example.umc10th.domain.review.dto.ReviewResDto;
import com.example.umc10th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping("{storeId}/reviews")
    public ApiResponse<ReviewResDto.CreateReview> createReview(
            @PathVariable Long storeId,
            @RequestBody ReviewReqDto.CreateReview request,
            @AuthenticationPrincipal Member member //시큐리티가 헤더 보고 주입
            ){
        ReviewResDto.CreateReview result= reviewService.createReview(storeId, member.getId(), request);
        return ApiResponse.onSuccess(ReviewSuccessCode.REVIEW_CREATED, result);
    }
}
