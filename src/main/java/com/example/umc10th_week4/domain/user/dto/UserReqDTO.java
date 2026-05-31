package com.example.umc10th_week4.domain.user.dto;

import com.example.umc10th_week4.domain.user.enums.FoodType;
import com.example.umc10th_week4.domain.user.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class UserReqDTO {

    @Getter
    public static class UserInfoRequest {
        private Long id;
    }

    @Getter
    public static class SignUpRequest {

        @NotBlank(message = "이메일은 빈칸일 수 없습니다.")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @NotBlank(message = "비밀번호는 빈칸일 수 없습니다.")
        private String password;

        @NotBlank(message = "이름은 빈칸일 수 없습니다.")
        private String name;

        @NotBlank(message = "닉네임은 빈칸일 수 없습니다.")
        private String nickname;

        private Gender gender;
        private LocalDate birth;
        private String address;
        private String phoneNumber;

        private List<FoodType> foodList;

        @NotNull(message = "약관 동의 정보는 필수입니다.")
        private AgreeRequest agree;
    }

    @Getter
    public static class AgreeRequest {
        private boolean age;        // 만 14세 이상
        private boolean service;    // 서비스 이용약관 (필수)
        private boolean privacy;    // 개인정보 처리방침 (필수)
        private boolean location;   // 위치정보 제공 (선택)
        private boolean marketing;  // 마케팅 수신 동의 (선택)
    }
}