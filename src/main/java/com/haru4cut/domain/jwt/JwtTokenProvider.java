package com.haru4cut.domain.jwt;

import com.haru4cut.domain.user.UserRepository;
import com.haru4cut.domain.user.UserService;
import com.haru4cut.domain.user.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long TIMEUNIT;

    private Key key;

    private final UserRepository userRepository;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @PostConstruct
    private void init() {
        log.info("secretKey 인코딩 시작 : {}", secretKey);
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
        log.info("secretKey 인코딩 완료 : {}", key);
    }

    public String getUserId(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }


    public String createAccessToken(Long userId) {
        Date now = new Date();

        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));

        // 1시간의 기간을 가진 access token 생성
        return Jwts.builder().setIssuedAt(now)
                .setClaims(claims)
                .setExpiration(new Date(now.getTime() + TIMEUNIT))
                .signWith(key, SignatureAlgorithm.HS512).compact();
    }

    public String createRefreshToken(Long userId) {
        Date now = new Date();

        Claims claims = Jwts.claims().setSubject(String.valueOf(userId));

        // 2주의 기간을 가진 refresh token 생성
        return Jwts.builder().setIssuedAt(now)
                .setClaims(claims)
                .setExpiration(new Date(now.getTime() + 24 * 14 * TIMEUNIT))
                .signWith(key, SignatureAlgorithm.HS512).compact();
    }

    public boolean validateToken(String token) throws Exception{
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        return !claims.getBody().getExpiration().before(new Date());
    }

    public void getAuthentication(Long userId) {

        Users user = userRepository.findById(userId).get();

        UserDetails userDetails = User.builder().username(user.getName())
                .password("default").authorities(user.getRole().getAuthority())
                .build();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                null, authoritiesMapper.mapAuthorities(userDetails.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

}
