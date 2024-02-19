package com.haru4cut.event;

import lombok.Data;

import java.util.List;

@Data
public class EventRequestDto {
    int emotion;
    int cutNum;
    List<String> keywords;

    public EventRequestDto(int emotion, int cutNum, List<String> keywords) {
        this.emotion = emotion;
        this.cutNum = cutNum;
        this.keywords = keywords;
    }
}
