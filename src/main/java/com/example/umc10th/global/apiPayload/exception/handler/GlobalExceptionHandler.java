package com.example.umc10th.global.apiPayload.exception.handler;

import com.example.umc10th.global.apiPayload.ApiResponse;
import com.example.umc10th.global.apiPayload.code.ReasonDTO;
import com.example.umc10th.global.apiPayload.code.status.ErrorStatus;
import com.example.umc10th.global.apiPayload.exception.GeneralException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ApiResponse<Object> handleGeneralException(GeneralException e) {
        ReasonDTO reason = e.getCode().getReasonDTO();

        return ApiResponse.onFailure(
                reason.getCode(),
                reason.getMessage(),
                null
        );
    }

    // @Valid 검증 실패 예외 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return ApiResponse.onFailure(
                ErrorStatus._BAD_REQUEST.getCode(),
                ErrorStatus._BAD_REQUEST.getMessage(),
                errors
        );
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> handleException(Exception e) {
        return ApiResponse.onFailure(
                ErrorStatus._INTERNAL_SERVER_ERROR.getCode(),
                ErrorStatus._INTERNAL_SERVER_ERROR.getMessage(),
                null
        );
    }
}