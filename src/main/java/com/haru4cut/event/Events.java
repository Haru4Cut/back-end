package com.haru4cut.event;

import com.haru4cut.domain.user.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Getter
@NoArgsConstructor
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventId")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users;

    private Long count = 0L;

    @Column(name = "keywords")
    private String[] keywords;

    @Column(name = "emotion")
    private int emotion;

    @Column(name = "cutNum")
    private int cutNum;

    @Column(name = "date")
    private String date;

    @Column(name = "url")
    private String url;

    @Column(name = "orderNum")
    private int orderNum;

    public Events(Users users, String url) {
        this.users = users;
        this.url = url;
    }

    public void updateLike(Long count){
        this.count = count;
    }

}

