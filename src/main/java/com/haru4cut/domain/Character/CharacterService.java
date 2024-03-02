package com.haru4cut.domain.Character;

import com.haru4cut.domain.user.UserRepository;
import com.haru4cut.domain.user.Users;
import com.haru4cut.global.exception.CustomException;
import com.haru4cut.global.exception.ErrorCode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CharacterService {

    private final UserRepository userRepository;
    private final CharacterRepository characterRepository;

    public Long saveCharacter(Long userId, CharacterRequestDto characterRequestDto) {

        Users user;
        try {
            user = userRepository.findById(userId).get();
        } catch (RuntimeException e) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }

        Character character = Character.builder().sex(characterRequestDto.getSex()).age(characterRequestDto.getAge()).hairColor(characterRequestDto.getHairColor())
                .hairLength(characterRequestDto.getHairLength()).skinColor(characterRequestDto.getSkinColor())
                .nickName(characterRequestDto.getNickName()).characterImg(characterRequestDto.getCharacterImage()).users(user)
                .etc(characterRequestDto.getEtc()).build();

        return characterRepository.save(character).getId();
    }

    @PostConstruct
    public void assingUser() {
        userRepository.save(Users.builder().email("test@test.com").password("test").build());
    }


}
