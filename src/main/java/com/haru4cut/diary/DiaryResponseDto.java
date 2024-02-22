package com.haru4cut.diary;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DiaryResponseDto {
    Long diaryId;
    String text;
    List<String> imgLinks;
}
