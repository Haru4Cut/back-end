package com.haru4cut.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class CommentController {
    @Autowired
    private final CommentService commentService;

    @PostMapping("/comments/{diaryId}")
    public ResponseEntity<CommentFlaskResponseDto> getComment(@PathVariable Long diaryId){
        CommentFlaskResponseDto dto = commentService.makeComment(diaryId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
