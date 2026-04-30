package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ReviewReqDTO {

    // 리뷰 작성
    public record WriteReview(
            @NotNull
            Long memberId,

            @NotNull
            Long storeId,

            @NotNull
            @DecimalMin(value = "0.0")
            @DecimalMax(value = "5.0")
            Float score,

            @NotBlank
            @Size(max = 500)
            String content
    ) {}
}
