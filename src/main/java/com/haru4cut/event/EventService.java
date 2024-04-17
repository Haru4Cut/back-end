package com.haru4cut.event;

import com.haru4cut.S3.ByteToMultiPartFile;
import com.haru4cut.S3.S3Uploader;
import com.haru4cut.dalle.APIService;
import com.haru4cut.domain.user.UserRepository;
import com.haru4cut.domain.user.Users;
import com.haru4cut.global.exception.CustomException;
import com.haru4cut.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventService {
    private List<String> promptList;
    private final APIService apiService;
    private byte[][] base64;
    private ByteToMultiPartFile byteToMultiPartFile;
    private S3Uploader s3Uploader;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    private final EventFlaskService eventFlaskService;

    // S3 링크 받아와서 출력하는 코드
    public List<String> createEvents(Long userId, List<EventRequestDto> events) throws IOException {
        List<String> eventsList = new ArrayList<>();
        Users users = userRepository.findById(userId).get();
        byte[][] base64 = getImgB64(events);
        int cutNum = getCutNum(events);
        String url = null;
        for(int i = 0; i < cutNum ; i++) {
            MultipartFile multipartFile = byteToMultiPartFile.changeByte(base64[i], events.get(0).date, events.get(i).orderNum, users.getId());
            url = s3Uploader.saveFile(multipartFile);
            String date = events.get(0).getDate();
            Events event = eventRepository.save(new Events(users, url, date));
            eventsList.add(event.getId() + " : " + url);
        }
        return eventsList;
    }

    // 각 컷 수 마다 Base64 형태로 그림 받아올 수 있도록 나누어 놓음
        private byte[][] getImgB64(List<EventRequestDto> events) {
        promptList = new ArrayList<>();
        promptList = makePrompt(events);
        int cutNum = getCutNum(events);
        if(cutNum == 1) {
            base64 = getOneB64(promptList);
        }
        if(cutNum == 2){
            base64 = getTwoB64(promptList);
        }
        if(cutNum == 4){
            base64 = getFourSameB64(promptList);
        }
        return base64;
    }

    private List<String> makePrompt(List<EventRequestDto> events){
        List<EventFlaskRequestDto> flaskEvent = new ArrayList<>();
        for(int i = 0; i < events.size(); i++){
            int emotionNum = events.get(i).getEmotion();
            String emotion = Emotions.getEmotion(emotionNum);

            EventFlaskRequestDto eventFlaskRequestDto = new EventFlaskRequestDto(
                    emotion,
                    events.get(i).getCutNum(),
                    events.get(i).getKeywords(),
                    events.get(i).getDate(),
                    events.get(i).getOrderNum()
            );

            flaskEvent.add(eventFlaskRequestDto);
        }
        List<String> promptList = eventFlaskService.sendToFlask(flaskEvent);
        return promptList;
    }

    private byte[][] getOneB64(List<String> promptList){
        String b64 = null;
        byte[][] base64Array = new byte[1][];
        for(int i = 0; i < promptList.size();i++){
            String prompt = promptList.get(i);
            b64 = apiService.generateOnePicture(prompt);
            base64Array[i] = Base64.decodeBase64(b64);
        }
        return base64Array;
    }

    private byte[][] getTwoB64(List<String> promptList){
        String b64 = null;
        byte[][] base64Array = new byte[2][];
        for(int i = 0; i < promptList.size();i++){
            String prompt = promptList.get(i);
            b64 = apiService.generateWidthPicture(prompt);
            base64Array[i] = Base64.decodeBase64(b64);
        }
        return base64Array;
    }

    private byte[][] getFourSameB64(List<String> promptList){
        String b64 = null;
        byte[][] base64Array = new byte[4][];
        for(int i = 0; i < promptList.size();i++){
            String prompt = promptList.get(i);
            b64 = apiService.generateFourPicture(prompt);
            base64Array[i] = Base64.decodeBase64(b64);
        }
        return base64Array;
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



}
