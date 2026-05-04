package com.example.umc10th_week4.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

public class UserResDTO {

    @Getter
    @Builder
    public static class UserInfoResponse {
        private String name;
        private String email;
        private String phoneNumber;
        private Integer point;
    }

    // ↓ 이거 추가!
    @Getter
    @Builder
    public static class MyPageResponse {
        private String nickname;
        private String email;
        private String phoneNumber;
        private Integer point;
    }
}
