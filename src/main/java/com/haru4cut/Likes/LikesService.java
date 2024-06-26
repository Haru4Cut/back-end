package com.haru4cut.Likes;

import com.haru4cut.domain.user.UserRepository;
import com.haru4cut.domain.user.Users;
import com.haru4cut.event.EventRepository;
import com.haru4cut.event.Events;
import com.haru4cut.global.exception.CustomException;
import com.haru4cut.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LikesService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private LikesRepository likesRepository;

    public LikesResponseDto postLike(Long userId, LikeDto likeDto) {
        Optional<Users> findUsers = userRepository.findById(userId);
        if(findUsers.isEmpty()){
            throw new CustomException("존재하지 않는 사용자 입니다.", HttpStatus.NOT_FOUND);
        }
        Events event = eventRepository.findEventsByUrl(likeDto.url);
        Optional<Events> findEvents = eventRepository.findById(event.getId());
        if(findEvents.isEmpty()){
            throw new CustomException("존재하지 않는 그림 입니다.", HttpStatus.NOT_FOUND);
        }
        Users users = userRepository.findUserById(userId);
        Events events = eventRepository.findEventsById(findEvents.get().getId());
        Optional<Likes> likes = likesRepository.findByUsersAndEvents(users,events);
        if(likes.isEmpty()){
            likesRepository.save(LikesRequestDto.toEntity(users,events));
            events.updateLike(1L);
        }
        if(likes.isPresent()) {
            likesRepository.deleteById(likes.get().id);
            events.updateLike(0L);
        }
        return new LikesResponseDto(events.getCount());
    }

    public LikesUsersResponseDto getLikes(Long userId){
        Optional<Users> findUsers = userRepository.findById(userId);
        if(findUsers.isEmpty()){
            throw new CustomException("존재하지 않는 사용자 입니다.", HttpStatus.NOT_FOUND);
        }
        Users users = userRepository.findUserById(userId);
        List<Events> eventsList = eventRepository.findEventsByUsers(users);
        List<String> likesList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        for(int i = 0; i < eventsList.size(); i++){
            Events events = eventsList.get(i);
            Optional<Likes> findLikes = likesRepository.findLikesByEvents(events);
            if (findLikes.isEmpty()){
                continue;
            }
            if (findLikes.isPresent()){
                likesList.add(events.getUrl());
                dateList.add(events.getDate());
            }
        }
        return new LikesUsersResponseDto(likesList, dateList);
    }

    public long getLike(LikeDto likeDto){
        Events events = eventRepository.findEventsByUrl(likeDto.getUrl());
        Optional<Likes> likes = likesRepository.findLikesByEvents(events);
        if(likes.isPresent()){
            return 1l;
        }
        return 0l;
    }



}
