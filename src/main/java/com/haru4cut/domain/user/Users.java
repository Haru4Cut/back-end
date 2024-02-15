package com.haru4cut.domain.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public Users(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
