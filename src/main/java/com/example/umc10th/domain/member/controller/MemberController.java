// java
package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.service.MemberService;
import com.example.umc10th.domain.member.dto.MemberReqDTO;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입 (Public)
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/auth/sign-up")
    public ApiResponse<MemberResDTO.SignUp> signUp(
            @RequestBody MemberReqDTO.SignUp dto
    ) {
        return ApiResponse.onSuccess(MemberSuccessCode.SIGN_UP, memberService.signUp(dto));
    }

    // 마이페이지 (Private)
    @PostMapping("/api/v1/users/me")
    public ApiResponse<MemberResDTO.GetInfo> getInfo(
            @RequestBody MemberReqDTO.GetInfo dto
    ) {
        return ApiResponse.onSuccess(MemberSuccessCode.OK, memberService.getInfo(dto));
    }
}
