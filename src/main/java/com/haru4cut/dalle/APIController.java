package com.haru4cut.dalle;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2
@RequiredArgsConstructor
public class APIController {
    private final APIService apiService;
    @PostMapping("/diaries/events")
    public ResponseEntity<?> generateImage(@RequestBody String prompt){
        return new ResponseEntity<>(apiService.generatePicture(prompt), HttpStatus.OK);
    }
}
