package com.haru4cut.domain.Character;

import com.haru4cut.domain.s3.S3ProfileUploader;
import com.haru4cut.domain.user.UserRepository;
import com.haru4cut.domain.user.Users;
import com.haru4cut.global.exception.CustomException;
import com.haru4cut.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CharacterService {

    private final UserRepository userRepository;
    private final CharacterRepository characterRepository;
    private final S3ProfileUploader s3ProfileUploader;

    public Long saveCharacter(Long userId, CharacterRequestDto characterRequestDto) throws IOException {

        Users user;
        try {
            user = userRepository.findById(userId).get();
        } catch (RuntimeException e) {
            throw new CustomException("존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND);
        }
        String profileUri = s3ProfileUploader.copyFile(String.valueOf(user.getId()));

        Characters character = Characters.builder().sex(characterRequestDto.getSex()).age(characterRequestDto.getAge()).hairColor(characterRequestDto.getHairColor())
                .hairLength(characterRequestDto.getHairLength()).skinColor(characterRequestDto.getSkinColor())
                .nickName(characterRequestDto.getNickName()).characterImg(profileUri).users(user)
                .etc(characterRequestDto.getEtc()).build();

        return characterRepository.save(character).getId();
    }

    public CharacterResponseDto editCharacter(Long userId, CharacterRequestDto characterRequestDto) throws IOException {

        Optional<Users> user = findUser(userId);

        Optional<Characters> characterOptional = characterRepository.findByUsers(user.get());

        if (characterOptional.isEmpty()) {
            throw new CustomException("존재하지 않는 캐릭터 입니다.", HttpStatus.NOT_FOUND);
        }
        String profileUri = s3ProfileUploader.copyFile(String.valueOf(userId));
        characterRequestDto.setCharacterImage(profileUri);
        Characters character = characterOptional.get();
        character.updateCharacter(characterRequestDto);
        characterRepository.save(character);

        return new CharacterResponseDto(character.getUsers().getId(), character.getId(), character.getNickName(), character.getCharacterImg());
    }

    public CharacterResponseDto editCharacterName(Long userId, String nickName) {
        Optional<Users> user = findUser(userId);

        Optional<Characters> characterOptional = characterRepository.findByUsers(user.get());
        if (characterOptional.isEmpty()) {
            throw new CustomException("존재하지 않는 캐릭터 입니다.", HttpStatus.NOT_FOUND);
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
            throw new CustomException("존재하지 않는 캐릭터 입니다.", HttpStatus.NOT_FOUND);
        }
        Characters character = characterOptional.get();

        return new CharacterVo(character.getSex(), character.getAge(), character.getHairColor(), character.getHairLength(), character.getSkinColor()
                , character.getNickName(), character.getCharacterImg(), character.getEtc(), user.get().getPencils());
    }

    private Optional<Users> findUser(Long userId) {
        Optional<Users> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new CustomException("존재하지 않는 사용자 입니다.", HttpStatus.NOT_FOUND);
        }

        return user;
    }

}
