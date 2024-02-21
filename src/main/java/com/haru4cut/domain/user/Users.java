package com.haru4cut.domain.user;

import com.haru4cut.diary.Diary;
import com.haru4cut.event.Events;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @Column(name = "password", length = 20, nullable = false)
    private String password;

    @Column(name = "memberId", length = 20, unique = true, nullable = false)
    private String email;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Diary> diaries = new ArrayList<>();
    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
