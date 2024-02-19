package com.haru4cut.event;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EventServiceTest {
    private EventService eventService;
    List<List<String>> keywords = new ArrayList<>();
    @Test
    @DisplayName("키워드에 맞춰 달리 이용해 일기 return")
    void makeKeyword(){
        //given
        int emotion = 1;
        int cutNum = 2;
        List<String> list1 = Arrays.asList("아침", "집에서", "혼자", "샐러드", "먹었다");
        List<String> list2 = Arrays.asList("점심", "공원에서", "남동생과 축구를 했다");
        keywords.add(list1);
        keywords.add(list2);
        //when
        List<String> result = eventService.makeEvent(keywords);
        //then
        for(int i = 0; i < keywords.size(); i++){
            String url = result.get(i);
            assertThat(url).startsWith("https://");
        }
    }

}