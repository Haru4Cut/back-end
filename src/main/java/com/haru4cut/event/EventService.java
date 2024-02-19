package com.haru4cut.event;

import com.haru4cut.dalle.APIService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EventService {
    public List<String> imgLinks = new ArrayList<>();
    public List<String> promptList = new ArrayList<>();
    private final APIService apiService;

    public List<String> getImgLinks(List<EventRequestDto> events){
        List<String> promptList = makePrompt(events);
        for(String prompt : promptList){
            String url = apiService.generatePicture(prompt);
            imgLinks.add(url);
        }
        return imgLinks;
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

    private String makeKeyword(List<String> keywords){
        StringBuilder makeString = new StringBuilder();
        for(String keyword : keywords){
            makeString.append(keyword);
            makeString.append(" ");
        }
        return makeString.toString();
    }


    private String getEmotions(int emotionNum){
        String emotion = Emotions.getEmotion(emotionNum);
        return emotion;
    }

}
