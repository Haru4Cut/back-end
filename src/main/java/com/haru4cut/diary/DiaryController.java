package com.haru4cut.diary;

import com.haru4cut.domain.user.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
@RequiredArgsConstructor
public class DiaryController {
    @Autowired
    private final DiaryService diaryService;

    @PostMapping("/diaries/{userId}")
    public ResponseEntity<DiaryResponseDto> createDiary(@PathVariable Long userId,
                                            @RequestBody DiaryRequestDto diaryRequestDto){
        Long diaryId = diaryService.createDiary(userId,diaryRequestDto);
        DiaryResponseDto diaryResponseDto = new DiaryResponseDto(diaryId, diaryRequestDto.getText(), diaryRequestDto.getImgLinks(), diaryRequestDto.getDate());
        return new ResponseEntity<>(diaryResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/diaries/{diaryId}")
    public ResponseEntity<DiaryResponseDto> updateDiary(@PathVariable Long diaryId,
                                             @RequestBody DiaryRequestDto diaryRequestDto) {
        Long newDiaryId = diaryService.updateDiary(diaryId, diaryRequestDto.getText());
        DiaryResponseDto diaryResponseDto = diaryService.findDiaryByDiaryId(newDiaryId);
        return new ResponseEntity<>(diaryResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/diaries/{diaryId}")
    public ResponseEntity<?> deleteDiary(@PathVariable Long diaryId){
        diaryService.deleteDiary(diaryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/diaries/{diaryId}")
    public ResponseEntity<DiaryResponseDto> findDiary(@PathVariable Long diaryId){
        DiaryResponseDto diaryResponseDto = diaryService.findDiaryByDiaryId(diaryId);
        return new ResponseEntity<>(diaryResponseDto, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/diaries")
    public ResponseEntity<List<DiaryResponseDto>> findDiaries(@PathVariable Long userId){
        List<DiaryResponseDto> diaryResponseDtoList = diaryService.findDiariesByUserId(userId);
        return new ResponseEntity<>(diaryResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/diarybydate")
    public ResponseEntity<DiaryResponseDto> getDiaryByDate(@PathVariable Long userId,
                                                           @RequestParam(name = "date") String date){
        DiaryResponseDto diaryResponseDto = diaryService.findDiaryByDate(userId, date);
        return new ResponseEntity<>(diaryResponseDto, HttpStatus.OK);
    }

}
