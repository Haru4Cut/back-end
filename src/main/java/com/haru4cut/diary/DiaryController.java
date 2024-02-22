package com.haru4cut.diary;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Log4j2
@RequiredArgsConstructor
public class DiaryController {
    @Autowired
    private final DiaryService diaryService;

    @PostMapping("/diaries/{userId}")
    public ResponseEntity<Long> createDiary(@PathVariable Long userId,
                                            @RequestBody DiaryRequestDto diaryRequestDto){
        Long diaryId = diaryService.createDiary(userId,diaryRequestDto);
        return new ResponseEntity(new DiaryResponseDto(diaryId), HttpStatus.CREATED);
    }

    @PatchMapping("/diaries/{diaryId}")
    public ResponseEntity<Long> updateDiary(@PathVariable Long diaryId,
                                             @RequestBody DiaryRequestDto diaryRequestDto) {
        Long newDiaryId = diaryService.updateDiary(diaryId, diaryRequestDto.getText());
        return new ResponseEntity(new DiaryResponseDto(newDiaryId), HttpStatus.OK);
    }

    @DeleteMapping("/diaries/{diaryId}")
    public ResponseEntity<?> deleteDiary(@PathVariable Long diaryId){
        diaryService.deleteDiary(diaryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
