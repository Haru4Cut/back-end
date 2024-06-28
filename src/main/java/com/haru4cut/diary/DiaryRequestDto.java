package com.haru4cut.diary;

import com.haru4cut.domain.user.Users;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DiaryRequestDto {
    private Users users;
    int cutNum;
    List<String> imgLinks;
    String text;
    String date;

    public Diary toEntity(Users users, String text, List<String> imgLinks, int cutNum, String date){
        return new Diary(users, cutNum, imgLinks, text, date);
    }

}
