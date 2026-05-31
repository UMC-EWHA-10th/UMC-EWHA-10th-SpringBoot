package com.example.umc10th.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

public class MemberResDTO {

    @Getter
    @Builder
    public static class MyPageResultDTO {
        private Long memberId;
        private String nickname;
        private String email;
        private String phoneNumber;
        private Integer point;
    }
}