package com.haru4cut.diary;

import com.haru4cut.domain.user.UserRepository;
import com.haru4cut.domain.user.Users;
import com.haru4cut.global.exception.CustomException;
import com.haru4cut.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DiaryService {
    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private UserRepository userRepository;

    public Long createDiary(Long userId, DiaryRequestDto diaryRequestDto) {
        Users users = userRepository.findById(userId).get();
        Diary diary = diaryRepository.save(diaryRequestDto.toEntity(
                users,
                diaryRequestDto.getText(),
                diaryRequestDto.getImgLinks(),
                diaryRequestDto.getCutNum(),
                diaryRequestDto.getDate()
                        )
        );
        return diary.getId();
    }

    public Long updateDiary(Long diaryId, String text){
        Optional<Diary> findDiary = diaryRepository.findById(diaryId);
        if(!findDiary.isPresent()){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        Diary diary = findDiary.get();
        diary.update(text);
        diaryRepository.save(diary);
        return diary.getId();
    }

    public void deleteDiary(Long diaryId) {
        Optional<Diary> findDiary = diaryRepository.findById(diaryId);
        if(!findDiary.isPresent()){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        diaryRepository.deleteById(diaryId);
    }


    public DiaryResponseDto findDiaryByDiaryId(Long diaryId) {
        Optional<Diary> findDiary = diaryRepository.findById(diaryId);
        if(findDiary.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        Diary diary = diaryRepository.findDiaryById(diaryId);
        return new DiaryResponseDto(diary.getId(), diary.getText(), diary.getImgLinks(), diary.getDate());
    }

    public List<DiaryResponseDto> findDiariesByUserId(Long userId){
        Users user = userRepository.findUserById(userId);
        List<Diary> list = diaryRepository.findDiariesByUsers(user);
        return list.stream()
                .map(diary -> new DiaryResponseDto(diary.getId(), diary.getText(),diary.getImgLinks(), diary.getDate()))
                .collect(Collectors.toList());
    }
}
