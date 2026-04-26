package com.example.umc10th.domain.member.dto;

import lombok.Builder;
import lombok.Getter;

public class MemberResDTO {

    @Getter
    @Builder
    public static class MyPageResultDTO {
        private String name;
        private String profileUrl;
        private String email;
        private String phoneNumber;
        private Integer point;
    }
}