package com.haru4cut.event;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;



@Entity
public class Events {

    @Id
    @Column(name = "eventId")
    private int id;

    @Column(name = "keywords")
    private String[] keywords;

    @Column(name = "emotion")
    private int emotion;

    @Column(name = "cutNum")
    private int cutNum;

//    @Column(name = "like")
//    private int like;

    @Column(name = "frame")
    private int frame;
}
