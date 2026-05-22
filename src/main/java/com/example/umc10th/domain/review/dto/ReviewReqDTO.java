package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class ReviewReqDTO {

    @Getter
    public static class CreateReviewDTO {

        @NotNull(message = "회원 ID는 필수입니다.")
        private Long memberId;

        @NotNull(message = "가게 ID는 필수입니다.")
        private Long storeId;

        @NotNull(message = "별점은 필수입니다.")
        private Float score;

        @NotBlank(message = "리뷰 내용은 필수입니다.")
        private String body;
    }

    @Getter
    public static class CreateReplyDTO {

        @NotNull(message = "리뷰 ID는 필수입니다.")
        private Long reviewId;

        @NotBlank(message = "답글 내용은 필수입니다.")
        private String body;
    }

    @Getter
    public static class MyReviewRequestDTO {

        @NotNull(message = "회원 ID는 필수입니다.")
        private Long memberId;
    }
}