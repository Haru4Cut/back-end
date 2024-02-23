package com.haru4cut.domain.oauth2;

public interface LoginService {
    public LoginDto signInUser(String authorizationCode);
}
