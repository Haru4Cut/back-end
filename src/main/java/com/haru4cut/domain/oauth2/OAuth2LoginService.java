package com.haru4cut.domain.oauth2;

import com.haru4cut.domain.jwt.JwtTokenProvider;
import com.haru4cut.domain.user.Users;
import com.haru4cut.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Primary
@RequiredArgsConstructor
@Service
public class OAuth2LoginService implements LoginService {

    private final OAuth2Service oAuth2Service;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public LoginDto signInUser(String authorizationCode) {
        OAuthAttributes userInfo = oAuth2Service.getUserInfo(authorizationCode);

        String oAuthId = userInfo.getOAuthId();
        SocialType socialType = userInfo.getSocialType();
        String name = userInfo.getName();

        HttpStatus httpStatus = HttpStatus.OK;
        Optional<Users> user = userRepository.findBySocialIdAndSocialType(oAuthId, socialType);
        Long userId = ( user.isPresent() ? user.get().getId() : null );

        // 회원 가입 유저
        if (user.isEmpty()) {
            userId = createUser(oAuthId, socialType, name);
            httpStatus = HttpStatus.CREATED;
        }

        // 우리 서버의 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(userId);

        return LoginDto.builder().accessToken(accessToken).userId(userId).name(name).httpStatus(httpStatus).build();
    }


    public Long createUser(String socialId, SocialType socialType, String name) {
        return userRepository.save(Users.builder().socialType(socialType).socialId(socialId).name(name).pencils(16).build()).getId();
    }
}
