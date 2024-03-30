package com.haru4cut.event;

import com.haru4cut.S3.ByteToMultiPartFile;
import com.haru4cut.S3.S3Uploader;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@AllArgsConstructor
@Log4j2
public class EventController {
    @Autowired
    private final EventService eventService;

    @Autowired
    private final S3Uploader s3Uploader;
    @Autowired
    private ByteToMultiPartFile byteToMultiPartFile;



    @PostMapping("/diaries/{userId}/events")
    public ResponseEntity<EventResponseDto> createEvent(@PathVariable(name = "userId") Long userId,
                                                        @RequestBody List<EventRequestDto> events) throws Exception{
        List<String> url = eventService.createEvents(userId, events);
        EventResponseDto eventResponseDto = new EventResponseDto(url);
        return new ResponseEntity<>(eventResponseDto, HttpStatus.OK);
    }

}
