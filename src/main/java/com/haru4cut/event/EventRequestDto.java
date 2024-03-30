package com.haru4cut.event;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EventRequestDto {
    int emotion;
    int cutNum;
    List<String> keywords;
    String date;
    int orderNum;
    public EventRequestDto(int emotion, int cutNum, List<String> keywords, String date, int orderNum) {
        this.emotion = emotion;
        this.cutNum = cutNum;
        this.keywords = keywords;
        this.date = date;
        this.orderNum = orderNum;
    }
}
