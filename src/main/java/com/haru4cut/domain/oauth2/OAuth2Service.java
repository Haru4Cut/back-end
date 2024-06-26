package com.haru4cut.domain.oauth2;

import com.haru4cut.global.exception.CustomException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class OAuth2Service {

    @Value("${oauth2.provider.token-uri}")
    private String TOKEN_URI;

    @Value("${oauth2.provider.user-info-uri}")
    private String USER_INFO_URI;

    @Value("${oauth2.client.client-id}")
    private String CLIENT_ID;

    @Value("${oauth2.client.redirect-uri}")
    private String REDIRECT_URI;

    public String getAccessToken(String authorizationCode) {

           // set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            // set body
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "authorization_code");
            body.add("client_id", CLIENT_ID);
            body.add("redirect_uri", REDIRECT_URI);
            body.add("code", authorizationCode);

            HttpEntity httpEntity = new HttpEntity(body, headers);

            RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Map> exchange = restTemplate.exchange(TOKEN_URI, HttpMethod.POST, httpEntity, Map.class);
            Map attributes = exchange.getBody();
            String accessToken = (String) attributes.get("access_token");
//            String refreshToken = (String) attributes.get("refresh_token");
            return accessToken;
        } catch (Exception e) {
            throw new CustomException("토큰 획득에 실패했습니다", HttpStatus.BAD_REQUEST);
        }
    }

    // 토큰으로 사용자 정보 받아오는 메서드
    public OAuthAttributes getUserInfo(String authorizationCode){

        /**
         * [KAKAO REST API] 요청할 정보 입력 - URI, Headers
         */
        String accessToken = getAccessToken(authorizationCode);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity httpEntity = new HttpEntity(headers);

        RestTemplate restTemplate = new RestTemplate();

        try {
            Map<String, Object> attributes = restTemplate.exchange(USER_INFO_URI, HttpMethod.GET, httpEntity, Map.class).getBody();
            return OAuthAttributes.ofKakao(attributes);
        } catch (Exception e) {
            throw new ResourceAccessException("Resource 접근에 실패하였습니다.");
        }
    }


}
