package com.haru4cut.domain.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityTestController {

    @GetMapping("/security")
    public String getAuthenticationTest() {
        return "ok";
    }

}
