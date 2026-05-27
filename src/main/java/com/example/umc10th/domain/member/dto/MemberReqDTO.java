package com.example.umc10th.domain.member.dto;

import com.example.umc10th.domain.member.enums.FoodName;
import com.example.umc10th.domain.member.enums.Gender;
import com.example.umc10th.domain.member.enums.TermName;
import com.example.umc10th.domain.mission.enums.Address;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class MemberReqDTO {

    public record GetInfo(
            Long id
    ) {}

    public record SignUp(
            String name,
            Gender gender,
            @JsonFormat(pattern = "yyyy-MM-dd") LocalDate birth,
            Address address,
            String detailAddress,
            String email,
            String password,
            String phoneNumber,
            List<FoodName> foodNameList,
            List<TermName> termNameList
    ) {}
}
