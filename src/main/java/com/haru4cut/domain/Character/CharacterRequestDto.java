package com.haru4cut.domain.Character;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CharacterRequestDto {

    private Long sex;
    private Long age;
    private Long hairColor;
    private Long hairLength;
    private Long skinColor;
    private String nickName;
    private String characterImage;
    private String etc;

    @Builder
    public CharacterRequestDto(Long sex, Long age, Long hairColor, Long hairLength, Long skinColor, String nickName, String characterImage, String etc) {
        this.sex = sex;
        this.age = age;
        this.hairColor = hairColor;
        this.hairLength = hairLength;
        this.skinColor = skinColor;
        this.nickName = nickName;
        this.characterImage = characterImage;
        this.etc = etc;
    }

}
