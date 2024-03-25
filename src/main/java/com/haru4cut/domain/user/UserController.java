package com.haru4cut.domain.user;

import com.haru4cut.global.exception.CustomException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Long> userSignUp(@Valid @RequestBody UserSignUpRequestDto userSignUpRequestDto) {
        Long userId = userService.createUser(userSignUpRequestDto);
        return new ResponseEntity(new UserResponseDto(userId), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity userDelete(@RequestBody Map<String, Long> data) {
        userService.deleteUser(data.get("userId"));
        return ResponseEntity.ok().build();
    }



    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException exception) {
        HttpStatus httpStatus = exception.getErrorCode().getHttpStatus();
        String errorMessage = exception.getErrorCode().getErrorMessage();
        return new ResponseEntity<>(errorMessage, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationException(Exception exception) {
        return ResponseEntity.badRequest().body("요청이 잘못되었습니다.");
    }   
}
