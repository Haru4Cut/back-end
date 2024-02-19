package com.haru4cut.event;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@AllArgsConstructor
@Log4j2
public class EventController {
    @Autowired
    private final EventService eventService;

    @PostMapping("/diaries/events")
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody List<EventRequestDto> events) throws Exception{
        List<String> imgLinks = eventService.getImgLinks(events);
        EventResponseDto eventResponseDto = new EventResponseDto(imgLinks);
        return new ResponseEntity<>(eventResponseDto, HttpStatus.OK);
    }
}
