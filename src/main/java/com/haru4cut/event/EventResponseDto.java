package com.haru4cut.event;

import lombok.Data;

import java.util.List;

@Data
public class EventResponseDto {
    List<String> imgLinks;
    public EventResponseDto(List<String> imgLinks) {
        this.imgLinks = imgLinks;
    }

}
