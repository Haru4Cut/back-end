package com.haru4cut.Likes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LikesServiceTest {
    private LikesRepository likesRepository;
    private LikesService likesService;
    @Test
    @DisplayName("좋아요 생성")
    void postLike(){
        //given
        Long userId = 1l;
        Long eventId = 1l;
        //when
        likesService.createLike(userId, eventId);
        //then
        Optional<Likes> likes = likesRepository.findLikesFromUsersAndEvents(userId,eventId);
        assertThat(likes.isPresent()).isTrue();
    }

    @Test
    @DisplayName("좋아요 삭제")
    void deleteLike(){
        //given
        Long userId = 1l;
        Long eventId = 1l;
        //when
        likesService.deleteLike(userId,eventId);
        //then
        Optional<Likes> likes = likesRepository.findLikesFromUsersAndEvents(userId,eventId);
        assertThat(likes.isEmpty()).isTrue();

    }

}