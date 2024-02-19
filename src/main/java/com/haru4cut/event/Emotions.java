package com.haru4cut.event;

public enum Emotions {
    기쁨(1),
    슬픔(2),
    분노(3),
    쏘쏘(4);

    private final int emotionNum;

    Emotions(int emotionNum) {
        this.emotionNum = emotionNum;
    }

    public static String getEmotion(int number){
        for (Emotions emotion : Emotions.values()){
            if(emotion.emotionNum == number){
                return emotion.toString();
            }
        }
        return 기쁨.toString();
    }

}
