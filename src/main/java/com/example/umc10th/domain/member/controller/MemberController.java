package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class MemberController {

    @PostMapping("/me")
    public ApiResponse<MemberResDTO.MyPageResultDTO> getMyPage(
            @RequestBody MemberReqDTO.MyPageRequestDTO request
    ) {
        MemberResDTO.MyPageResultDTO result = MemberResDTO.MyPageResultDTO.builder()
                .name("nickname012")
                .profileUrl("https://~~")
                .email("dlapdlf@naver.com")
                .phoneNumber(null)
                .point(2500)
                .build();

        return ApiResponse.of(MemberSuccessCode.MEMBER_INFO_SUCCESS, result);
    }
}