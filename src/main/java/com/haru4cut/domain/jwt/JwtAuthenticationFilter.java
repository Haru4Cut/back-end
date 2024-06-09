package com.haru4cut.domain.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtTokenProvider jwtTokenProvider;
    private final String[] REQUEST_PERMITTED = {"/h2-console","/h2-console/.*", "/users/login/.*", "/test"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("요청 URI : {}", request.getRequestURI());

        if(CorsUtils.isPreFlightRequest(request)){
            return ;
        }

        try {
            Optional<String> accessToken = Optional.ofNullable(request.getHeader("Authorization").split(" ")[1]);
            if (accessToken.isEmpty() || !jwtTokenProvider.validateToken(accessToken.get())) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "인증에 실패하였습니다.");
                return;
            }

                Long userId = Long.parseLong(jwtTokenProvider.getUserId(accessToken.get()));
            jwtTokenProvider.getAuthentication(userId);

        } catch (Exception e) {
            log.error("validateToken Error!");
        }

        filterChain.doFilter(request, response);

    }
}
