package com.haru4cut.event;

import lombok.Data;

import java.util.List;

@Data
public class EventRequestDto {
    int emotion;
    int cutNum;
    List<String> keywords;
    int frame;
    public EventRequestDto(int emotion, int cutNum, List<String> keywords, int frame) {
        this.emotion = emotion;
        this.cutNum = cutNum;
        this.keywords = keywords;
        this.frame = frame;
    }
}
