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
}