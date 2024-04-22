package com.haru4cut.Likes;

import com.haru4cut.event.EventResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

    @PostMapping("likes/{userId}/{eventId}")
    public ResponseEntity<LikesResponseDto> createLikes(@PathVariable Long userId,
                                                        @PathVariable Long eventId){

        LikesResponseDto likesResponseDto = likesService.postLike(userId,eventId);
        return new ResponseEntity<>(likesResponseDto, HttpStatus.OK);
    }

    @GetMapping("likes/{userId}")
    public ResponseEntity<LikesUsersResponseDto> getLikesEvents(@PathVariable Long userId){
        LikesUsersResponseDto eventResponseDto = likesService.getLikes(userId);
        return new ResponseEntity<>(eventResponseDto, HttpStatus.OK);
    }


}
