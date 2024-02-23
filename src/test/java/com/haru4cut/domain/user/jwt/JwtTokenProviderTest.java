package com.haru4cut.domain.user.jwt;

import com.haru4cut.domain.jwt.JwtTokenProvider;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@SpringBootTest(classes = JwtTokenProvider.class)
class JwtTokenProviderTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long TIMEUNIT;

    private Key key;

    @Test
    public void testTokenValidate() throws Exception {

        //given
        Date now = new Date();
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
        String expirationToken = Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date(now.getTime() + 500))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        //when
        String accessToken = jwtTokenProvider.createAccessToken(1l);
        String refreshToken = jwtTokenProvider.createRefreshToken(1l);
        Thread.sleep(1000);


        //then
        Assertions.assertThat(jwtTokenProvider.validateToken(accessToken)).isTrue();
        Assertions.assertThat(jwtTokenProvider.validateToken(refreshToken)).isTrue();
        Assertions.assertThat(jwtTokenProvider.validateToken(expirationToken)).isFalse();
    }

}