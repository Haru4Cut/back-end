package com.haru4cut.domain.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum UserRole implements GrantedAuthority {

    ROLE_USER("ROLE_USER"),
    ROLE_SUBSCRIBER("ROLE_SUBSCRIBER");

    private String value;

    UserRole(String value) {
        this.value = value;
    }

    @Override
    public String getAuthority() {
        return this.value;
    }
}
