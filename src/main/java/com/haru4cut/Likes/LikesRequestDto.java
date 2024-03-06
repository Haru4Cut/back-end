package com.haru4cut.Likes;

import com.haru4cut.domain.user.Users;
import com.haru4cut.event.Events;
import com.haru4cut.Likes.Likes;
public class LikesRequestDto {
    Users users;
    Events events;

    public static Likes toEntity(Users users, Events events) {
        return Likes.builder()
                .users(users)
                .events(events)
                .build();
    }


}
