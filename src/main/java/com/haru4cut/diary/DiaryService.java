package com.haru4cut.diary;

import com.haru4cut.domain.user.UserRepository;
import com.haru4cut.domain.user.Users;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DiaryService {
    private DiaryRepository diaryRepository;
    private UserRepository userRepository;

    public Long createDiary(Long userId, DiaryRequestDto diaryRequestDto) {
        Users users = userRepository.findById(userId).get();
        Diary diary = diaryRepository.save(diaryRequestDto.toEntity(
                users,
                diaryRequestDto.getText(),
                diaryRequestDto.getImgLinks(),
                diaryRequestDto.getCutNum()
                        )
        );
        return diary.getDiaryId();
    }
}
