package com.haru4cut.domain.oauth2;

import com.haru4cut.domain.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class LoginController {

    private final OAuth2Service oAuth2Service;
    private final LoginService loginService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/users/login/{AuthorizationCode}")
    public ResponseEntity userLogin(HttpServletRequest request, @PathVariable(name = "AuthorizationCode") String authorizationCode) {
        LoginDto loginDto = loginService.signInUser(authorizationCode);
        LoginSuccessDto loginSuccessDto = new LoginSuccessDto(loginDto.getUserId(), loginDto.getEmail(), loginDto.getAccessToken());

        // 최초 로그인 유저에게는 201 상태코드로 응답
        if (loginDto.getHttpStatus() == HttpStatus.CREATED) {
            return new ResponseEntity(loginSuccessDto, HttpStatus.CREATED);
        }

        // 기존 유저에게는 200 상태코드로 응답
        return new ResponseEntity(loginSuccessDto, HttpStatus.OK);
    }
}
