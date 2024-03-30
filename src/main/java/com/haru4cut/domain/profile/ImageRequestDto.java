package com.haru4cut.domain.profile;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ImageRequestDto {

    private Long sex;
    private Long age;
    private Long hairColor;
    private Long hairLength;
    private Long skinColor;
    private String etc;

    public ImageRequestDto(Long sex, Long age, Long hairColor, Long hairLength, Long skinColor, String etc) {
        this.sex = sex;
        this.age = age;
        this.hairColor = hairColor;
        this.hairLength = hairLength;
        this.skinColor = skinColor;
        this.etc = etc;
    }
}
