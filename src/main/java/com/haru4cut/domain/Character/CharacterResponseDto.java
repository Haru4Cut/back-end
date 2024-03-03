package com.haru4cut.domain.Character;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterResponseDto {

    private Long userId;
    private Long characterId;
    private String nickName;
    private String characterImg;

    public CharacterResponseDto(Long userId, Long characterId, String nickName, String characterImg) {
        this.userId = userId;
        this.characterId = characterId;
        this.nickName = nickName;
        this.characterImg = characterImg;
    }
}
