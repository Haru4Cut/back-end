package com.haru4cut.dalle;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class APIServiceTest {
    @Autowired
    private APIService apiService;

    @Test
    @DisplayName("API Service에서 url을 잘 반환 하는지 체크")
    void getUrlTest(){
        //given
        String prompt = "귀여운 강아지 그려줘 웹툰 느낌으로 !";
        //when
        String result = apiService.generateOnePicture(prompt);
        //then
        assertThat(result).startsWith("https://");

    }


}