package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.FoodName;
import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.member.enums.TermName;
import com.example.umc10th.domain.mission.enums.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {

    public record GetInfo(
            Long id
    ) {}

    public record SignUp(
            @NotBlank(message = "이름은 필수입니다.")
            @Size(max = 50, message = "이름은 50자 이하여야 합니다.")
            String name,

            @NotNull(message = "성별은 필수입니다.")
            Gender gender,

            @NotNull(message = "생년월일은 필수입니다.")
            @JsonFormat(pattern = "yyyy-MM-dd")
            LocalDate birth,

            @NotNull(message = "지역은 필수입니다.")
            Address address,

            String detailAddress,

            @NotBlank(message = "이메일은 필수입니다.")
            @Email(message = "이메일 형식이 올바르지 않습니다.")
            String email,

            @NotBlank(message = "비밀번호는 필수입니다.")
            @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
            String password,

            String phoneNumber,

            @NotEmpty(message = "음식 취향은 최소 1개 이상 선택해야 합니다.")
            List<FoodName> foodNameList,

            @NotEmpty(message = "약관 동의는 최소 1개 이상 필요합니다.")
            List<TermName> termNameList
    ) {}
}
