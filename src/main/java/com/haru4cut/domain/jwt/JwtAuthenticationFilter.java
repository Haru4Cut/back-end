package com.haru4cut.domain.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
// "/.*", "*"
    private final String[] REQUEST_PERMITTED = {"/h2-console","/h2-console/.*", "/favicon.ico", "/users/login/.*", "/.*"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("요청 URI : {}", request.getRequestURI());

        if (Arrays.stream(REQUEST_PERMITTED).anyMatch(r -> request.getRequestURI().matches(r))) {
            log.info("로그인 요청 시 필터 통과");
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = request.getHeader("Authorization").split(" ")[1];

        if (accessToken == null || accessToken.isBlank() || !jwtTokenProvider.validateToken(accessToken)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증에 실패하였습니다.");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
