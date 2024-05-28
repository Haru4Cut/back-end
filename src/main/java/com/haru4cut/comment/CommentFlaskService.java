package com.haru4cut.comment;

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
public class CommentFlaskService {
    static String MODEL_REQUEST_URL = "";

    public CommentFlaskResponseDto getCommentToFlask(List<Object> dto){
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<CommentFlaskResponseDto> responseEntity = restTemplate.exchange(
                MODEL_REQUEST_URL,
                HttpMethod.POST,
                new HttpEntity<>(dto),
                new ParameterizedTypeReference<CommentFlaskResponseDto>() {}
        );
        return responseEntity.getBody();
    }

}
