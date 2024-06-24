package com.haru4cut.comment;

import com.haru4cut.diary.Diary;
import com.haru4cut.diary.DiaryRepository;
import com.haru4cut.domain.user.Users;
import com.haru4cut.event.Emotions;
import com.haru4cut.event.EventService;
import com.haru4cut.global.exception.CustomException;
import com.haru4cut.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class CommentService {

    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private EventService eventService;

    @Autowired
    private CommentFlaskService commentFlaskService;
    @Autowired
    private CommentRepository commentRepository;

    public CommentFlaskResponseDto makeComment(Long diaryId){
        Optional<Diary> diary = diaryRepository.findById(diaryId);
        List<Object> result = new ArrayList<>();
        if(diary.isEmpty()){
            throw new CustomException("존재하지 않는 일기 입니다.", HttpStatus.NOT_FOUND);
        }
        Diary c_diary = diaryRepository.findDiaryById(diaryId);
        int cutNum = diary.get().getCutNum();
        String contents = diary.get().getText();
        List<String> keyList = eventService.getKeywordAndEmotion(diary.get().getId());
        for(int i = 0; i < keyList.size(); i++){
            String[] parts = keyList.get(i).split(":");
            int emotion = Integer.parseInt(parts[0]);
            List<String> keywords = Arrays.asList(parts[1]);
            String real_emotion = Emotions.getEmotion(emotion);

            Map<String, Object> commentMap = new HashMap<>();
            commentMap.put("cutNum", cutNum);
            commentMap.put("emotion", real_emotion);
            commentMap.put("keywords", keywords);

            result.add(commentMap);
        }
        if (!contents.trim().isEmpty()) {
            Map<String, String> contentMap = new HashMap<>();
            contentMap.put("content", contents);
            result.add(contentMap);
        }
        CommentFlaskResponseDto comments = commentFlaskService.getCommentToFlask(result);
        Users users = diary.get().getUsers();
        commentRepository.save(CommentRequestDto.toEntity(users,c_diary,comments.getContents()));
        return comments;
    }

    public CommentFlaskResponseDto getComments(Long diaryId){
        Optional<Diary> diary = diaryRepository.findById(diaryId);
        if(diary.isEmpty()){
            throw new CustomException("존재하지 않는 일기 입니다.", HttpStatus.NOT_FOUND);
        }
        Diary diary1 = diary.get();
        Optional<Comments> comments = commentRepository.findCommentsByDiary(diary1);
        if(comments.isEmpty()){
            throw new CustomException("코멘트가 존재하지 않습니다", HttpStatus.NOT_FOUND);
        }
        CommentFlaskResponseDto commentFlaskResponseDto = new CommentFlaskResponseDto(comments.get().getContents());
        return commentFlaskResponseDto;
    }
}
