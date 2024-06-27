package com.haru4cut.domain.Character;

import com.haru4cut.global.exception.CustomException;
import com.haru4cut.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@RequestMapping(value = "/character")
@RequiredArgsConstructor
@RestController
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping("/{userId}")
    public ResponseEntity createCharacter(@RequestBody CharacterRequestDto characterRequestDto, @PathVariable(name = "userId") Long userId) throws Exception {
        Long characterId;
        try {
            characterId = characterService.saveCharacter(userId, characterRequestDto);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        ConcurrentHashMap<String, Long> body = new ConcurrentHashMap<>();
        body.put("characterId", characterId);
        return ResponseEntity.ok(body);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity updateCharacter(@RequestBody CharacterRequestDto characterRequestDto, @PathVariable(name = "userId") Long userId) throws IOException {
        CharacterResponseDto characterResponseDto = characterService.editCharacter(userId, characterRequestDto);
        return ResponseEntity.ok(characterRequestDto);
    }

    @PatchMapping("/{userId}/nickName")
    public ResponseEntity updateNickname(@PathVariable(name = "userId") Long userId, @RequestBody CharacterNickNameRequestDto CharacterNickNameRequestDto) {
        String nickName = CharacterNickNameRequestDto.nickName;

        if (nickName.length() <= 0 || nickName.length() >= 6) {
            return ResponseEntity.badRequest().body("닉네임의 길이는 1~5자까지 가능합니다.");
        }

        CharacterResponseDto characterResponseDto = characterService.editCharacterName(userId, nickName);
        return ResponseEntity.ok(characterResponseDto);
    }

    @GetMapping("/{userId}")
    public ResponseEntity getCharacter(@PathVariable(name = "userId") Long userId) {
        CharacterVo characterVo = characterService.findCharacter(userId);
        return ResponseEntity.ok(characterVo);
    }

//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<String> handleCustomException(CustomException exception) {
//        HttpStatus httpStatus = exception.getErrorCode().getHttpStatus();
//        String errorMessage = exception.getErrorCode().getErrorMessage();
//        return new ResponseEntity<>(errorMessage, httpStatus);
//    }

}
