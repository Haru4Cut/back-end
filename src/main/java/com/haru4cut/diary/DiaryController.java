package com.haru4cut.diary;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Log4j2
@RequiredArgsConstructor
public class DiaryController {
    @Autowired
    private final DiaryService diaryService;

    @PostMapping("/users/{userId}/diaries")
    public ResponseEntity<Long> createDiary(@PathVariable Long userId,
                                            @RequestBody DiaryRequestDto diaryRequestDto){
        Long diaryId = diaryService.createDiary(userId,diaryRequestDto);
        return new ResponseEntity(new DiaryResponseDto(diaryId), HttpStatus.CREATED);
    }


}
