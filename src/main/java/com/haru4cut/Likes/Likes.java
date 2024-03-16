package com.haru4cut.Likes;

import com.haru4cut.domain.user.Users;
import com.haru4cut.event.Events;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Builder
@AllArgsConstructor
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likeId")
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventId")
    private Events events;

    public Likes() {}


}
