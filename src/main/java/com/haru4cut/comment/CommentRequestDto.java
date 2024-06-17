package com.haru4cut.comment;

import com.haru4cut.diary.Diary;
import com.haru4cut.domain.user.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor

public class CommentRequestDto {
    Diary diary;
    Users users;
    String contents;

    public static Comments toEntity(Users users, Diary diary, String contents) {
        return Comments.builder()
                .diary(diary)
                .contents(contents)
                .users(users)
                .build();
    }
}
