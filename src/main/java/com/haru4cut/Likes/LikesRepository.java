package com.haru4cut.Likes;


import com.haru4cut.domain.user.Users;
import com.haru4cut.event.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    Optional<Likes> findByUsersAndEvents(Users users, Events events);
    List<Likes> findByUsers(Users users);
}
