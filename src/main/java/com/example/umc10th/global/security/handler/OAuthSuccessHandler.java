package com.example.umc10th.global.security.handler;

import com.example.umc10th.domain.member.converter.MemberConverter;
import com.example.umc10th.domain.member.dto.MemberResDTO;
import com.example.umc10th.domain.member.exception.code.MemberSuccessCode;
import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.BaseSuccessCode;
import com.example.umc10th.global.security.entity.AuthMember;
import com.example.umc10th.global.security.entity.OAuthMember;
import com.example.umc10th.global.security.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            BaseSuccessCode code = MemberSuccessCode.OK;

            // Content-Type, Status 설정
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(code.getStatus().value());

            // authentication 파라미터에서 직접 꺼내기 (SecurityContextHolder 대신)
            OAuthMember member = (OAuthMember) authentication.getPrincipal();

            // 토큰 제작
            String accessToken = jwtUtil.createAccessToken(new AuthMember(member.getMember()));

            // 응답 통일 객체 래핑
            ApiResponse<MemberResDTO.Login> responseBody = ApiResponse.onSuccess(
                    code,
                    MemberConverter.toLogin(accessToken)
            );

            // 응답 출력
            objectMapper.writeValue(response.getOutputStream(), responseBody);

        } catch (Exception e) {
            // 디버깅용: 실제 예외 원인 출력
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            objectMapper.writeValue(
                    response.getOutputStream(),
                    Map.of(
                            "isSuccess", false,
                            "code", "SUCCESS_HANDLER_ERROR",
                            "message", e.getMessage() != null ? e.getMessage() : "알 수 없는 오류",
                            "cause", e.getClass().getSimpleName()
                    )
            );
        }
    }
}
