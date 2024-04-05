package com.haru4cut.event;

import lombok.Data;

import java.util.List;

@Data
public class EventResponseDto {
    List<String> imgLinks;
    String date;
    public EventResponseDto(List<String> imgLinks, String date) {
        this.imgLinks = imgLinks;
        this.date = date;
    }

}
