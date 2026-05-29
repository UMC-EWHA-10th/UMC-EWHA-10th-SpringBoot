package com.example.umc10th.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

public class AuthResDTO {

    @Builder
    @Schema(name = "AuthLoginResponse")
    public record Login(
            String accessToken
    ) {}
}
