package com.haru4cut.domain.oauth2;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class LoginSuccessDto {

    private long userId;
    private String name;
    private String accessToken;

    public LoginSuccessDto(long userId, String name, String accessToken) {
        this.userId = userId;
        this.name = name;
        this.accessToken = accessToken;
    }
}
