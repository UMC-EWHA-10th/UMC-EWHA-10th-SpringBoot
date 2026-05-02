package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.entity.Member;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    // 마이페이지
    @GetMapping("/v1/users/me")
    public ApiResponse<MemberResDTO.GetInfo> getInfo(
            @AuthenticationPrincipal Member member)
    {
        BaseSuccessCode code= MemberSuccessCode.OK;
        return ApiResponse.onSuccess(code, memberService.getInfo(member));
    }

}
