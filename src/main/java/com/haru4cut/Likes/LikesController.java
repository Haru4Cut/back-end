package com.haru4cut.Likes;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

    @PostMapping("likes/{userId}/{eventId}")
    public ResponseEntity<?> createLikes(@PathVariable Long userId,
                                         @PathVariable Long eventId){

        likesService.postLike(userId,eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
