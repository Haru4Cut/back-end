package com.haru4cut.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NOT_FOUND(HttpStatus.NOT_FOUND, "없는 데이터입니다."),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "이미 존재하는 데이터입니다."),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 요청입니다."),

    FORBIDDEN(HttpStatus.FORBIDDEN, "요청에 대한 권한이 없습니다");

    private final HttpStatus httpStatus;
    private final String errorMessage;

}
