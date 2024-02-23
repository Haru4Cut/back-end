package com.haru4cut.domain.oauth2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class LoginSuccessDto {

    private long userId;
    private String email;
    private String accessToken;

    public LoginSuccessDto(long userId, String email, String accessToken) {
        this.userId = userId;
        this.email = email;
        this.accessToken = accessToken;
    }
}
