package com.haru4cut.domain.user;

import com.haru4cut.diary.Diary;
import com.haru4cut.domain.oauth2.SocialType;
import com.haru4cut.domain.security.UserRole;
import com.haru4cut.event.Events;
import com.haru4cut.purchase.Purchase;
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

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Transient
    @Column(name = "password", length = 20, nullable = false)
    private String password;

    @Column(length = 5)
    private String name;

    @Column(name = "socialId")
    private String socialId;

    @Column(name = "pencils")
    private int pencils;

    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.ROLE_SUBSCRIBER;
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Diary> diaries = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Events> events = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Purchase> purchases = new ArrayList<>();

    @Builder
    public Users(String email, String password, String name, String socialId, SocialType socialType, int pencils) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.socialId = socialId;
        this.socialType = socialType;
        this.pencils = pencils;
    }

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Users(Long id, int pencils){ // 연필 개수 갱신
        this.id = id;
        this.pencils = pencils;
    }


}
