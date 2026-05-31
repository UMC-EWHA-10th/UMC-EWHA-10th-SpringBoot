package com.example.umc10th.domain.member.exception.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.example.umc10th.global.apiPayload.code.BaseErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(
            HttpStatus.NOT_FOUND,
            "MEMBER404_1",
            "해당 유저를 찾을 수 없습니다."
    ),
    MEMBER_ALREADY_EXISTS(
            HttpStatus.CONFLICT,
            "MEMBER409_1",
            "이미 가입된 이메일입니다."
    ),
    NOT_SUPPORT_SOCIAL_PROVIDER(
            HttpStatus.BAD_REQUEST,
            "MEMBER400_1",
            "지원하지 않는 소셜 로그인입니다."
    ),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}
