package com.example.umc10th_week4.domain.user.dto;

import com.example.umc10th_week4.domain.user.enums.Gender;
import lombok.Getter;

import java.time.LocalDate;

public class UserReqDTO {

    @Getter
    public static class UserInfoRequest {
        private Long id;
    }

    @Getter
    public static class SignUpRequest {
        private String email;
        private String password;
        private String name;
        private String nickname;
        private Gender gender;
        private LocalDate birth;
        private String address;
        private String phoneNumber;
    }
}