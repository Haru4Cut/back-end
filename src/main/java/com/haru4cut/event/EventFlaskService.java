package com.haru4cut.event;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
public class EventFlaskService {
    static String MODEL_REQUEST_URL = "MODEL_REQUEST_URL";
    public List<String> sendToFlask(List<EventFlaskRequestDto> eventFlaskRequestDto) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<String>> responseEntity = restTemplate.exchange(
                MODEL_REQUEST_URL,
                HttpMethod.POST,
                new HttpEntity<>(eventFlaskRequestDto),
                new ParameterizedTypeReference<List<String>>() {}
        );
        return responseEntity.getBody();
    }
}
