package com.haru4cut.diary;

import com.haru4cut.domain.user.UserRepository;
import com.haru4cut.domain.user.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DiaryServiceTest {
    @Autowired
    private DiaryService diaryService;

    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("일기 쓰기 기능 테스트")
    void createDiaryTest() {
        //given
        Long userId = 1L;
        String date = "2022-01-08";
        Users user = new Users("wnstj1128@naver.com", "wnstj1819");
        userRepository.save(user);

        DiaryRequestDto requestDto = new DiaryRequestDto(user, 4, Arrays.asList(
                "http://localhost/image/image1.png",
                "http://localhost/image/image2.png",
                "http://localhost/image/image3.png",
                "http://localhost/image/image4.png"
        ), "오늘은 친구들이랑 밥을 먹었구여 아주 행복합니다앙", date);

        //when
        Long diaryId = diaryService.createDiary(userId, requestDto);

        //then
        assertNotNull(diaryId);
        assertTrue(diaryId > 0);
    }

    @Test
    @DisplayName("일기 수정 테스트 - text만 수정 가능")
    void updateDiaryTest(){
        //given
        Long userId = 1L;
        String date = "2022-01-08";
        Users user = new Users("wnstj1128@naver.com", "wnstj1819");
        userRepository.save(user);

        DiaryRequestDto requestDto = new DiaryRequestDto(user, 4, Arrays.asList(
                "http://localhost/image/image1.png",
                "http://localhost/image/image2.png",
                "http://localhost/image/image3.png",
                "http://localhost/image/image4.png"
        ), "오늘은 친구들이랑 밥을 먹었구여 아주 행복합니다앙", date);

        Long diaryId = diaryService.createDiary(userId, requestDto);
        String text = "바뀌었나요?";
        //when
        diaryService.updateDiary(diaryId, text);
        //then
        Optional<Diary> diary = diaryRepository.findById(diaryId);
        assertEquals(text, diary.get().getText());
    }

    @Test
    @DisplayName("일기 삭제 테스트")
    void deleteDiaryTest(){
        //given
        Long userId = 1L;
        Users user = new Users("wnstj1128@naver.com", "wnstj1819");
        String date = "2022-01-08";
        userRepository.save(user);
        DiaryRequestDto requestDto = new DiaryRequestDto(user, 4, Arrays.asList(
                "http://localhost/image/image1.png",
                "http://localhost/image/image2.png",
                "http://localhost/image/image3.png",
                "http://localhost/image/image4.png"
        ), "오늘은 친구들이랑 밥을 먹었구여 아주 행복합니다앙", date);
        Long diaryId = diaryService.createDiary(userId, requestDto);
        //when
        diaryService.deleteDiary(diaryId);
        //then
        assertFalse(diaryRepository.findById(diaryId).isPresent());
    }

}