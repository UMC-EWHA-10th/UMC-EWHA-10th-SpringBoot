package com.example.umc10th_week4.domain.user.dto;

import com.example.umc10th_week4.domain.user.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;

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
    }
}