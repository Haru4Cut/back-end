package com.haru4cut.Likes;

import com.haru4cut.event.EventResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

    @PostMapping("likes/{userId}")
    public ResponseEntity<LikesResponseDto> createLikes(@PathVariable Long userId,
                                                        @RequestBody LikeDto likeDto){
        LikesResponseDto likesResponseDto = likesService.postLike(userId,likeDto);
        return new ResponseEntity<>(likesResponseDto, HttpStatus.OK);
    }

    @GetMapping("likes/users/{userId}")
    public ResponseEntity<LikesUsersResponseDto> getLikesEvents(@PathVariable Long userId){
        LikesUsersResponseDto eventResponseDto = likesService.getLikes(userId);
        return new ResponseEntity<>(eventResponseDto, HttpStatus.OK);
    }

    @PostMapping("likes/events")
    public ResponseEntity<Long> getLikes(@RequestBody LikeDto likeDto){
        Long likes = likesService.getLike(likeDto);
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }

}
