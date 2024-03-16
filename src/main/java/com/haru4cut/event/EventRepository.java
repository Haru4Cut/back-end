package com.haru4cut.event;

import com.haru4cut.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Events, Long> {
    Events findEventsById(Long eventId);
    List<Events> findEventsByUsers(Users users);
}
