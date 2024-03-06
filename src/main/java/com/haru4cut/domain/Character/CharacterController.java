package com.haru4cut.domain.Character;

import com.haru4cut.global.exception.CustomException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ConcurrentHashMap;

@RequestMapping(value = "/character")
@RequiredArgsConstructor
@RestController
public class CharacterController {

    private final CharacterService characterService;

    @PostMapping("/{userId}")
    public ResponseEntity createCharacter(CharacterRequestDto characterRequestDto, @PathVariable(name = "userId") Long userId) {
        Long characterId = characterService.saveCharacter(userId, characterRequestDto);
        ConcurrentHashMap<String, Long> body = new ConcurrentHashMap<>();
        body.put("characterId", characterId);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{characterId}")
    public ResponseEntity updateCharacter(CharacterRequestDto characterRequestDto, @PathVariable(name = "characterId") Long characterId) {
        CharacterResponseDto characterResponseDto = characterService.editUser(characterId, characterRequestDto);
        return ResponseEntity.ok(characterRequestDto);
    }

    @GetMapping("/{characterId}")
    public ResponseEntity getCharacter(@PathVariable(name = "characterId") Long characterId) {
        CharacterVo characterVo = characterService.findCharacter(characterId);
        return ResponseEntity.ok(characterVo);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException exception) {
        HttpStatus httpStatus = exception.getErrorCode().getHttpStatus();
        String errorMessage = exception.getErrorCode().getErrorMessage();
        return new ResponseEntity<>(errorMessage, httpStatus);
    }

}
