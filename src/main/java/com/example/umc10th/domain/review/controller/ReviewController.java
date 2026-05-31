package com.example.umc10th.domain.review.controller;

import com.example.umc10th.domain.review.dto.ReviewReqDTO;
import com.example.umc10th.domain.review.dto.ReviewResDTO;
import com.example.umc10th.domain.review.enums.ReviewSortType;
import com.example.umc10th.domain.review.exception.code.ReviewSuccessCode;
import com.example.umc10th.domain.review.service.ReviewService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping("/v1/stores/{store_id}/reviews")
    public ApiResponse<ReviewResDTO.WriteReview> writeReview(
            @PathVariable("store_id") Long storeId,
            @Valid @RequestBody ReviewReqDTO.WriteReview dto
    ) {
        BaseSuccessCode code = ReviewSuccessCode.OK;
        return ApiResponse.onSuccess(code, reviewService.writeReview(storeId, dto));
    }

    /**
     * 내가 작성한 리뷰 목록 조회 (커서 페이징, 사진 제외)
     * - sort=ID  : 리뷰 ID 내림차순. cursorId 만 사용
     * - sort=STAR: 별점 내림차순, 동점 시 ID 내림차순. cursorStar + cursorId 사용
     * 첫 페이지는 cursor 파라미터들을 모두 생략.
     */
    @GetMapping("/v1/reviews/me")
    public ApiResponse<ReviewResDTO.MyReviewList> getMyReviewList(
            @RequestParam Long memberId,
            @RequestParam(defaultValue = "ID") ReviewSortType sort,
            @RequestParam(required = false) Float cursorStar,
            @RequestParam(required = false) Long cursorId
    ) {
        BaseSuccessCode code = ReviewSuccessCode.LIST_OK;
        return ApiResponse.onSuccess(
                code,
                reviewService.getMyReviewList(memberId, sort, cursorStar, cursorId)
        );
    }
}
