package com.example.chap4.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ReviewRequestDTO {

    public record MyReviewRequest(
            @NotNull(message = "멤버 ID는 필수입니다.")
            Long memberId,

            Long cursor,

            @NotNull(message = "페이지 크기는 필수입니다.")
            @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
            Integer size,

            String sortBy
    ) {
    }
}