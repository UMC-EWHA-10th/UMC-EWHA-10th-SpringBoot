package com.example.umc10th.domain.member.controller;

import com.example.umc10th.domain.member.dto.AuthReqDTO;
import com.example.umc10th.domain.member.dto.AuthResDTO;
import com.example.umc10th.domain.member.service.AuthService;
import com.example.umc10th.global.apiPayload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    // 회원가입 API - Public API
    @PostMapping("/signup")
    public ApiResponse<AuthResDTO.SignUpResultDTO> signUp(
            @Valid @RequestBody AuthReqDTO.SignUpRequestDTO request
    ) {
        AuthResDTO.SignUpResultDTO result = authService.signUp(request);

        return ApiResponse.onSuccess(result);
    }

    // 로그인 API - Public API, JWT 발급
    @PostMapping("/login")
    public ApiResponse<AuthResDTO.LoginResultDTO> login(
            @Valid @RequestBody AuthReqDTO.LoginRequestDTO request
    ) {
        AuthResDTO.LoginResultDTO result = authService.login(request);

        return ApiResponse.onSuccess(result);
    }
}