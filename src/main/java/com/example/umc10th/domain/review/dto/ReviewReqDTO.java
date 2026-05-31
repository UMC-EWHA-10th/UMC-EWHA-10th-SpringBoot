package com.example.umc10th.domain.review.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ReviewReqDTO {

    // 리뷰 작성
    public record WriteReview(
            @NotNull(message = "memberId는 필수입니다.")
            @Positive(message = "memberId는 양수여야 합니다.")
            Long memberId,

            @NotNull(message = "score는 필수입니다.")
            @DecimalMin(value = "0.0", message = "score는 0.0 이상이어야 합니다.")
            @DecimalMax(value = "5.0", message = "score는 5.0 이하여야 합니다.")
            Float score,

            @NotBlank(message = "content는 비어 있을 수 없습니다.")
            @Size(max = 500, message = "content는 최대 500자까지 입력 가능합니다.")
            String content
    ) {}
}
