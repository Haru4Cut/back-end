package com.haru4cut.domain.Character;

import com.haru4cut.domain.user.Users;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "character")
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "characterId")
    private Long id;

    @OneToOne
    @JoinColumn(name = "userid")
    private Users users;

    @Column(nullable = false)
    private Long sex;

    @Column(nullable = false)
    private Long age;

    @Column(nullable = false)
    private Long hairColor;

    @Column(nullable = false)
    private Long hairLength;

    @Column(nullable = false)
    private Long skinColor;

    private String nickName;

    private String characterImg;

    private String etc;

    @Builder
    public Character(Users users, Long sex, Long age, Long hairColor, Long hairLength, Long skinColor, String nickName, String characterImg, String etc) {
        this.users = users;
        this.sex = sex;
        this.age = age;
        this.hairColor = hairColor;
        this.hairLength = hairLength;
        this.skinColor = skinColor;
        this.nickName = nickName;
        this.characterImg = characterImg;
        this.etc = etc;
    }

    public void updateCharacterImg(CharacterRequestDto characterRequestDto) {
        this.sex = characterRequestDto.getSex();
        this.age = characterRequestDto.getAge();
        this.hairColor = characterRequestDto.getHairColor();
        this.hairLength = characterRequestDto.getHairLength();
        this.nickName = characterRequestDto.getNickName();
        this.characterImg = characterRequestDto.getCharacterImage();
        this.etc = characterRequestDto.getEtc();
    }

}
