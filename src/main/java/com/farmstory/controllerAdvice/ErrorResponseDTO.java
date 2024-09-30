package com.farmstory.controllerAdvice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponseDTO {
    private int status; // HTTP 상태 코드
    private String error; // 에러 종류
    private String message; // 에러 메시지
}
