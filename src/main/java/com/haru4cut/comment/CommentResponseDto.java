package com.haru4cut.comment;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class CommentResponseDto {
    String emotion;
    List<String> keywords;

    String contents;

    public CommentResponseDto(String emotion, List<String> keywords, String contents) {
        this.emotion = emotion;
        this.keywords = keywords;
        this.contents = contents;
    }

    public CommentResponseDto(String emotion, List<String> keywords) {
        this.emotion = emotion;
        this.keywords = keywords;
    }
}
