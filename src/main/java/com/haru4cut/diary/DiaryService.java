package com.haru4cut.diary;

import com.haru4cut.domain.user.UserRepository;
import com.haru4cut.domain.user.Users;
import com.haru4cut.global.exception.CustomException;
import com.haru4cut.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        Optional<Users> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new CustomException("존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND);
        }
        Users users = user.get();
        System.out.println("요청을 하고 있는 users: "+ users);
        System.out.println("프론트에서 보내고 있는 date: " + diaryRequestDto.getDate());

        Optional<Diary> exist_diary = diaryRepository.findDiaryByUsersAndDate(users, diaryRequestDto.getDate());

        System.out.println("findDiaryByUsersAndDate에서 출력한 다이어리 아이디 : "+ exist_diary);

        if(exist_diary.isPresent()) {
            System.out.println("여기 찍히면 서비스 단에서 예외처리 부분은 들어온 것");
            throw new CustomException("오늘 이미 일기를 작성했습니다!", HttpStatus.CONFLICT);
        }
        Diary diary = diaryRepository.save(diaryRequestDto.toEntity(
                users,
                diaryRequestDto.getText(),
                diaryRequestDto.getImgLinks(),
                diaryRequestDto.getCutNum(),
                diaryRequestDto.getDate()
                        )
        );

        System.out.println("해당 하는 날짜에 일기가 없을 때 최종으로 찍히는 것: " + diary.getId());
        return diary.getId();
    }

    public Long updateDiary(Long diaryId, String text){
        Optional<Diary> findDiary = diaryRepository.findById(diaryId);
        if(findDiary.isEmpty()){
            throw new CustomException("존재하지 않는 일기입니다.", HttpStatus.NOT_FOUND);
        }
        Diary diary = findDiary.get();
        diary.update(text);
        diaryRepository.save(diary);
        return diary.getId();
    }

    public void deleteDiary(Long diaryId) {
        Optional<Diary> findDiary = diaryRepository.findById(diaryId);
        if(findDiary.isEmpty()){
            throw new CustomException("존재하지 않는 일기입니다.", HttpStatus.NOT_FOUND);
        }
        diaryRepository.deleteById(diaryId);
    }


    public DiaryResponseDto findDiaryByDiaryId(Long diaryId) {
        Optional<Diary> findDiary = diaryRepository.findById(diaryId);
        if(findDiary.isEmpty()){
            throw new CustomException("존재하지 않는 일기입니다.", HttpStatus.NOT_FOUND);
        }
        Diary diary = diaryRepository.findDiaryById(diaryId);
        return new DiaryResponseDto(diary.getId(), diary.getText(), diary.getImgLinks(), diary.getDate());
    }

    public List<DiaryResponseDto> findDiariesByUserId(Long userId){
        Optional<Users> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new CustomException("존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND);
        }
        Users users = userRepository.findUserById(user.get().getId());
        List<Diary> list = diaryRepository.findDiariesByUsers(users);
        return list.stream()
                .map(diary -> new DiaryResponseDto(diary.getId(), diary.getText(),diary.getImgLinks(), diary.getDate()))
                .collect(Collectors.toList());
    }

    public DiaryResponseDto findDiaryByDate(Long userId, String date){
        Optional<Users> findUser = userRepository.findById(userId);
        if (findUser.isEmpty()){
            throw new CustomException("존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND);
        }
        Users users = userRepository.findUserById(findUser.get().getId());
        Optional<Diary> findDiary = diaryRepository.findDiaryByUsersAndDate(users, date);
        if(findDiary.isEmpty()){
            throw new CustomException("존재하지 않는 일기입니다.", HttpStatus.NOT_FOUND);
        }
        Diary diary = diaryRepository.findDiaryById(findDiary.get().getId());
        return new DiaryResponseDto(diary.getId(), diary.getText(), diary.getImgLinks(),diary.getDate());

    }
}
