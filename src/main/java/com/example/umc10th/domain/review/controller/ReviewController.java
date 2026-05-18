package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
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
    public ApiResponse<ReviewResDTO.CreateReviewResultDTO> createReview(
            @Valid @RequestBody ReviewReqDTO.CreateReviewDTO request
    ) {
        ReviewResDTO.CreateReviewResultDTO result = reviewService.createReview(request);

        return ApiResponse.of(ReviewSuccessCode.REVIEW_CREATE_SUCCESS, result);
    }

    // 리뷰 답글 작성
    @PostMapping("/reply")
    public ApiResponse<ReviewResDTO.CreateReplyResultDTO> createReply(
            @Valid @RequestBody ReviewReqDTO.CreateReplyDTO request
    ) {
        ReviewResDTO.CreateReplyResultDTO result = reviewService.createReply(request);

        return ApiResponse.of(ReviewSuccessCode.REVIEW_CREATE_SUCCESS, result);
    }

    // 내가 작성한 리뷰 조회 - ID 순 커서 기반
    @PostMapping("/my/id")
    public ApiResponse<ReviewResDTO.MyReviewCursorResultDTO> getMyReviewsByIdCursor(
            @Valid @RequestBody ReviewReqDTO.MyReviewRequestDTO request,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        ReviewResDTO.MyReviewCursorResultDTO result =
                reviewService.getMyReviewsByIdCursor(
                        request.getMemberId(),
                        cursorId,
                        size
                );

        return ApiResponse.onSuccess(result);
    }

    // 내가 작성한 리뷰 조회 - 별점 순 커서 기반
    @PostMapping("/my/score")
    public ApiResponse<ReviewResDTO.MyReviewCursorResultDTO> getMyReviewsByScoreCursor(
            @Valid @RequestBody ReviewReqDTO.MyReviewRequestDTO request,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        ReviewResDTO.MyReviewCursorResultDTO result =
                reviewService.getMyReviewsByScoreCursor(
                        request.getMemberId(),
                        cursor,
                        size
                );

        return ApiResponse.onSuccess(result);
    }
}