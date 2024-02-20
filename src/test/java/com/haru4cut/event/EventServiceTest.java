package com.haru4cut.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EventServiceTest {
    @Autowired
    private EventService eventService;


    @Test
    @DisplayName("키워드에 맞춰 달리 이용해 일기 return")
    void makeEvent(){
        //given
        List<EventRequestDto> events = Arrays.asList(
                new EventRequestDto(4, 3, Arrays.asList("아빠랑", "카페에서", "커피를 마셨다"), 1),
                new EventRequestDto(4, 1, Arrays.asList("친구랑", "집에서", "생일 파티를 했다"),1),
                new EventRequestDto(4, 2, Arrays.asList("남자친구랑", "집에서", "보드게임을 했다"),1),
                new EventRequestDto(4, 2, Arrays.asList("혼자", "도서관에서", "공부를 했다"),1)
        );
        //when
        List<String> urlList = eventService.getImgLinks(events);
        //then
        for(String url : urlList){
            assertThat(url).startsWith("https://");
        }
    }

}