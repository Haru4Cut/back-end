package com.haru4cut.domain.Character;

import com.haru4cut.domain.user.UserRepository;
import com.haru4cut.domain.user.Users;
import com.haru4cut.global.exception.CustomException;
import com.haru4cut.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        Characters character = Characters.builder().sex(characterRequestDto.getSex()).age(characterRequestDto.getAge()).hairColor(characterRequestDto.getHairColor())
                .hairLength(characterRequestDto.getHairLength()).skinColor(characterRequestDto.getSkinColor())
                .nickName(characterRequestDto.getNickName()).characterImg(characterRequestDto.getCharacterImage()).users(user)
                .etc(characterRequestDto.getEtc()).build();

        return characterRepository.save(character).getId();
    }

    public CharacterResponseDto editCharacter(Long userId, CharacterRequestDto characterRequestDto) {

        Optional<Users> user = findUser(userId);

        Optional<Characters> characterOptional = characterRepository.findByUsers(user.get());

        if (characterOptional.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }

        Characters character = characterOptional.get();
        character.updateCharacter(characterRequestDto);
        characterRepository.save(character);

        return new CharacterResponseDto(character.getUsers().getId(), character.getId(), character.getNickName(), character.getCharacterImg());
    }

    public CharacterResponseDto editCharacterName(Long userId, String nickName) {
        Optional<Users> user = findUser(userId);

        Optional<Characters> characterOptional = characterRepository.findByUsers(user.get());
        if (characterOptional.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }

        Characters character = characterOptional.get();
        character.updateCharacterName(nickName);
        characterRepository.save(character);

        return new CharacterResponseDto(character.getUsers().getId(), character.getId(), character.getNickName(), character.getCharacterImg());
    }

    public CharacterVo findCharacter(Long userId) {

        Optional<Users> user = findUser(userId);

        Optional<Characters> characterOptional = characterRepository.findByUsers(user.get());
        if (characterOptional.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        Characters character = characterOptional.get();

        return new CharacterVo(character.getSex(), character.getAge(), character.getHairColor(), character.getHairLength(), character.getSkinColor()
                , character.getNickName(), character.getCharacterImg(), character.getEtc(), user.get().getPencils());
    }

    private Optional<Users> findUser(Long userId) {
        Optional<Users> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }

        return user;
    }

}
