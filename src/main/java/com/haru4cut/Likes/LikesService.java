package com.haru4cut.Likes;

import com.haru4cut.diary.Diary;
import com.haru4cut.diary.DiaryResponseDto;
import com.haru4cut.domain.user.UserRepository;
import com.haru4cut.domain.user.Users;
import com.haru4cut.event.EventRepository;
import com.haru4cut.event.Events;
import com.haru4cut.global.exception.CustomException;
import com.haru4cut.global.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LikesService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private LikesRepository likesRepository;

    public void postLike(Long userId, Long eventId) {
        Optional<Users> findUsers = userRepository.findById(userId);
        Optional<Events> findEvents = eventRepository.findById(eventId);
        if(!findUsers.isPresent() || !findEvents.isPresent()){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        Users users = userRepository.findUserById(userId);
        Events events = eventRepository.findEventsById(eventId);
        Optional<Likes> likes = likesRepository.findByUsersAndEvents(users,events);
        if(!likes.isPresent()){
            likesRepository.save(LikesRequestDto.toEntity(users,events));
        }
        if(likes.isPresent()) {
            likesRepository.deleteById(likes.get().id);
        }
    }

    public List<Likes> getLikes(Long userId){
        Optional<Users> findUsers = userRepository.findById(userId);
        if(!findUsers.isPresent()){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        Users users = userRepository.findUserById(userId);
        List<Likes> likeList = likesRepository.findByUsers(users);
        return likeList;
    }



}
