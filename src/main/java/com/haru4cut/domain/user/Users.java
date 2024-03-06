package com.haru4cut.domain.user;

import com.haru4cut.diary.Diary;
import com.haru4cut.domain.oauth2.SocialType;
import com.haru4cut.event.Events;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @Column(name = "email", length = 50, unique = true, nullable = false)
    private String email;

    @Transient
    @Column(name = "password", length = 20, nullable = false)
    private String password;

    @Column(length = 5)
    private String name;

    @Column(name = "socialId")
    private String socialId;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Events> events = new ArrayList<>();

    @Builder
    public Users(String email, String password, String name, String socialId, SocialType socialType) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.socialId = socialId;
        this.socialType = socialType;
    }

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
