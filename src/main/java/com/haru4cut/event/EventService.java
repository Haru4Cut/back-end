package com.haru4cut.event;

import com.haru4cut.dalle.APIService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    public List<String> imgLinks;
    public List<String> promptList;
    private final APIService apiService;

    public List<String> getImgLinks(List<EventRequestDto> events){
        List<String> promptList = makePrompt(events);
        int frameNum = getFrameNum(events);
        int cutNum = getCutNum(events);
        if(cutNum == 1){
            imgLinks = getOneImgLinks(promptList);
        }
        if(cutNum == 2){
            imgLinks = getTwoImgLinks(promptList);
        }
        if(cutNum == 4 && frameNum == 1){ // 같은 크기
            imgLinks = getFourSameImgLinks(promptList);
        }
        if(cutNum == 4 && frameNum == 2){
            imgLinks = getFourDifferentImgLinks(promptList);
        }

        return imgLinks;
    }

    private List<String> getOneImgLinks(List<String> promptList){
        for(String prompt : promptList){
            String url = apiService.generateOnePicture(prompt);
            imgLinks.add(url);
        }
        return imgLinks;
    }

    private List<String> getTwoImgLinks(List<String> promptList){
        for(String prompt : promptList){
            String url = apiService.generateWidthPicture(prompt);
            imgLinks.add(url);
        }
        return imgLinks;
    }

    private List<String> getFourSameImgLinks(List<String> promptList){
        for(String prompt : promptList){
            String url = apiService.generateFourPicture(prompt);
            imgLinks.add(url);
        }
        return imgLinks;
    }

    private List<String> getFourDifferentImgLinks(List<String> promptList){
        for(String prompt : promptList){
            String url = apiService.generateWidthPicture(prompt);
            imgLinks.add(url);
        }
        return imgLinks;
    }
    private int getFrameNum(List<EventRequestDto> events) {
        int frameNum = 0;
        for (EventRequestDto event : events) {
            frameNum = event.getFrame();
        }
        return frameNum;
    }



    private List<String> makePrompt(List<EventRequestDto> events) {
        for(EventRequestDto event : events){
            int emotionNum = event.getEmotion();
            String emotion = Emotions.getEmotion(emotionNum);
            List<String> keywords = event.getKeywords();
            String keyword = makeKeyword(keywords);
            promptList.add(emotion + " " + keyword);
        }
        return promptList;
    }

    private int getCutNum(List<EventRequestDto> events){
        int firstCutNum = events.get(0).getCutNum();
        for(EventRequestDto event : events) {
            if (event.getCutNum() != firstCutNum) {
                throw new IllegalArgumentException("올바른 형식의 컷 수가 아닙니다.");
            }
        }
        return firstCutNum;
    }

    private String makeKeyword(List<String> keywords){
        StringBuilder makeString = new StringBuilder();
        for(String keyword : keywords){
            makeString.append(keyword);
            makeString.append(" ");
        }
        return makeString.toString();
    }


}
