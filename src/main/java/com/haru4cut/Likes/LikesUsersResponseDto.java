package com.haru4cut.Likes;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LikesUsersResponseDto {

    List<String> imgLinks;
    List<String> date;
}
