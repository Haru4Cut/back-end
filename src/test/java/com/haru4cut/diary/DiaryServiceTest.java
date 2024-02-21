package com.haru4cut.diary;

import com.haru4cut.domain.user.UserRepository;
import com.haru4cut.domain.user.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

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
        Users user = new Users("wnstj1128@naver.com", "wnstj1819");
        userRepository.save(user);

        DiaryRequestDto requestDto = new DiaryRequestDto(user, 4, Arrays.asList(
                "http://localhost/image/image1.png",
                "http://localhost/image/image2.png",
                "http://localhost/image/image3.png",
                "http://localhost/image/image4.png"
        ), "오늘은 친구들이랑 밥을 먹었구여 아주 행복합니다앙");

        //when
        Long diaryId = diaryService.createDiary(userId, requestDto);

        //then
        assertNotNull(diaryId);
        assertTrue(diaryId > 0);
    }
}