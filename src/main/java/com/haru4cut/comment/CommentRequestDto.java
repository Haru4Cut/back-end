package com.haru4cut.comment;

import com.haru4cut.diary.Diary;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CommentRequestDto {
    Diary diary;
}
