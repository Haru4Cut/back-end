package com.haru4cut.domain.oauth2;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
public class LoginDto{

    private HttpStatus httpStatus;
    private long userId;
    private String email;
    private String accessToken;

    @Builder
    public LoginDto(HttpStatus httpStatus, long userId, String email, String accessToken) {
        this.httpStatus = httpStatus;
        this.userId = userId;
        this.email = email;
        this.accessToken = accessToken;
    }
}
