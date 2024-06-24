package com.haru4cut.domain.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haru4cut.global.exception.CustomException;
import com.haru4cut.global.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.io.OutputStream;

@Slf4j
public class CustomAuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    private static CustomException customException = new CustomException("해당 요청에 대한 권한이 없습니다.", HttpStatus.UNAUTHORIZED);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("UnAuthorizaed : " + authException.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        try(OutputStream outputStream = response.getOutputStream()){
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(outputStream, customException.getErrorCode());
        }
    }
}
