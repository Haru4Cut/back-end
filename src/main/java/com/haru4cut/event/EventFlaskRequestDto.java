package com.haru4cut.event;

import lombok.Data;

import java.util.List;

@Data
public class EventFlaskRequestDto {
    String emotion;
    int cutNum;
    List<String> keywords;
    String date;
    int orderNum;

    public EventFlaskRequestDto(String emotion, int cutNum, List<String> keywords, String date, int orderNum) {
        this.emotion = emotion;
        this.cutNum = cutNum;
        this.keywords = keywords;
        this.date = date;
        this.orderNum = orderNum;
    }

}
