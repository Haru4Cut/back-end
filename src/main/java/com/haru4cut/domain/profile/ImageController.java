package com.haru4cut.domain.profile;

import com.haru4cut.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ImageController {

    private final ImageService imageService;

    @PostMapping("image/{userId}")
    public ResponseEntity getProfileImage(@RequestBody ImageRequestDto imageRequestDto, @PathVariable(name = "userId") Long userId) {
        String imageUri = imageService.generateProfileImage(imageRequestDto, userId);
        return ResponseEntity.ok(imageUri);
    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException exception) {
        HttpStatus httpStatus = exception.getErrorCode().getHttpStatus();
        String errorMessage = exception.getErrorCode().getErrorMessage();
        return new ResponseEntity<>(errorMessage, httpStatus);
    }
}
