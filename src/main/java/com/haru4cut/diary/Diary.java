package com.haru4cut.diary;

import com.haru4cut.domain.user.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diaryId")
    private Long diaryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users users;

    @Column(name = "cutNum")
    private int cutNum;

    @Column(name = "text")
    private String text;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "imgLinks")
    private List<String> imgLinks;

    public Diary(Users users, int cutNum, List<String> imgLinks, String text) {
        this.users = users;
        this.cutNum = cutNum;
        this.imgLinks = imgLinks;
        this.text = text;
    }

    public void update(String text){
        this.text = text;
    }
}
