package com.example.umc10th.domain.auth.dto;

public class AuthReqDTO {

    public record Login(
            String email,
            String password
    ) {}
}
