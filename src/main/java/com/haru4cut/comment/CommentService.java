package com.haru4cut.comment;

import com.haru4cut.diary.Diary;
import com.haru4cut.diary.DiaryRepository;
import com.haru4cut.event.Emotions;
import com.haru4cut.event.EventService;
import com.haru4cut.global.exception.CustomException;
import com.haru4cut.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommentService {

    @Autowired
    private DiaryRepository diaryRepository;
    @Autowired
    private EventService eventService;

    @Autowired
    private CommentFlaskService commentFlaskService;

    public CommentFlaskResponseDto makeComment(Long diaryId){
        Optional<Diary> diary = diaryRepository.findById(diaryId);
        List<Object> result = new ArrayList<>();
        if(diary.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
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
        CommentFlaskResponseDto comment = commentFlaskService.getCommentToFlask(result);
        return comment;
    }
}
