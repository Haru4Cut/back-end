package com.haru4cut.domain.profile;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ModelClient {

    static String MODEL_PROFILE_REQUEST_URI = "http://43.200.188.243:8888/characters/makeprompt";

    public String requestPrompt (ImageRequestDto imageRequestDto){
        String result = "";

        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> exchange = restTemplate.exchange(MODEL_PROFILE_REQUEST_URI, HttpMethod.POST, new HttpEntity(imageRequestDto.asMap()), String.class);
            result = exchange.getBody();
            log.info("생성된 프롬프트 : {}", result);
        } catch (Exception e) {
            log.error("프롬프트 생성 실패");
        }

        return result;
    }

}
