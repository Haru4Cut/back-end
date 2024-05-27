package com.haru4cut.domain.profile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@NoArgsConstructor
@Data
public class ImageRequestDto {

    @JsonIgnore
    private static String properties[][] = {{"남성", "여성"}, {"10대", "20-30대","40-50대","60대 이상"}, {"단발","장발","숏컷"},
            {"검정", "갈색", "빨강", "금발"},{"하얀피부", "황인피부", "어두운피부"}};
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

    public HashMap<String, String> asMap() {
        HashMap<String, String> map = new HashMap<>();

        map.put("sex", properties[0][this.sex.intValue() - 1]); // 성별
        map.put("age", properties[1][this.age.intValue() - 1]); // 나이
        map.put("hairColor", properties[2][this.hairColor.intValue() - 1]); // 머리색
        map.put("hairLength", properties[3][this.hairLength.intValue() - 1]); // 머리길이
        map.put("skinColor", properties[4][this.skinColor.intValue() - 1]); // 피부색
        map.put("etc", this.etc);

        return map;
    }
}
