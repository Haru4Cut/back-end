package com.haru4cut.diary;

import com.haru4cut.domain.user.UserRepository;
import com.haru4cut.domain.user.Users;
import com.haru4cut.global.exception.CustomException;
import com.haru4cut.global.exception.ErrorCode;
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

    public Long updateDiary(Long diaryId, String text){
        Optional<Diary> findDiary = diaryRepository.findById(diaryId);
        if(!findDiary.isPresent()){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        Diary diary = findDiary.get();
        diary.update(text);
        diaryRepository.save(diary);
        return diary.getDiaryId();
    }

    public void deleteDiary(Long diaryId) {
        Optional<Diary> findDiary = diaryRepository.findById(diaryId);
        if(!findDiary.isPresent()){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        diaryRepository.deleteById(diaryId);
    }
}
