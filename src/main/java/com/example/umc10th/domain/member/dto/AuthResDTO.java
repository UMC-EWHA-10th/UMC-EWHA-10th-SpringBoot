package com.example.umc10th.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

public class AuthResDTO {

    @Getter
    @Builder
    public static class SignUpResultDTO {
        private Long memberId;
        private String email;
        private String nickname;
    }

    @Getter
    @Builder
    public static class LoginResultDTO {
        private Long memberId;
        private String email;
        private String nickname;
        private String accessToken;
    }
}