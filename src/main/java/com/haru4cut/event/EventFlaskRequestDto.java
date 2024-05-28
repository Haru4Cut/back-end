package com.haru4cut.event;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class EventFlaskRequestDto {
    String emotion;
    int cutNum;
    List<String> keywords;
    String date;
    int orderNum;
    HashMap<String, String> characterInfo;

    public EventFlaskRequestDto(String emotion, int cutNum, List<String> keywords, String date, int orderNum, HashMap<String, String> characterInfo) {
        this.emotion = emotion;
        this.cutNum = cutNum;
        this.keywords = keywords;
        this.date = date;
        this.orderNum = orderNum;
        this.characterInfo = characterInfo;
    }

}
