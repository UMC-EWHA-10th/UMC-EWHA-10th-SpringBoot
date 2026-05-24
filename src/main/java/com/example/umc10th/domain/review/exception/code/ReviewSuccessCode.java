package com.example.umc10th.domain.review.exception.code;

import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewSuccessCode implements BaseSuccessCode{

    // 리뷰 작성
    REVIEW_CREATED(HttpStatus.OK,
            "REVIEW200_1",
                    "성공적으로 리뷰를 작성했습니다."),
    // 리뷰 조회
    REVIEW_CHECKED(HttpStatus.OK, "REVIEW200_2", "성공적으로 리뷰를 불러왔습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}