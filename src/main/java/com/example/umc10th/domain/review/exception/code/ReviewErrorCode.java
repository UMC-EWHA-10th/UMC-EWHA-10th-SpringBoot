package com.example.umc10th.domain.review.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode {
    //리뷰 작성
    TOO_MANY_IMAGES(HttpStatus.BAD_REQUEST, "REVIEW400_1", "리뷰 사진은 최대 3장까지만 등록 가능합니다."),
    INVALID_STAR_RATING(HttpStatus.BAD_REQUEST, "REVIEW400_2", "별점은 1점에서 5점 사이여야 합니다."),
    REVIEW_OWNER_MISMATCH(HttpStatus.FORBIDDEN, "REVIEW403_1", "해당 리뷰를 수정/삭제할 권한이 없습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
