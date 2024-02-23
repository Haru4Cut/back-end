package com.haru4cut.domain.user;

import com.haru4cut.domain.oauth2.LoginDto;
import com.haru4cut.domain.oauth2.LoginService;
import com.haru4cut.global.exception.CustomException;
import com.haru4cut.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements LoginService {

    @Autowired
    private final UserRepository userRepository;

    public Long createUser(UserSignUpRequestDto userSignUpRequestDto) {

        String email = userSignUpRequestDto.getEmail();
        String password = userSignUpRequestDto.getPassword();

        Optional<Users> userFound = userRepository.findByEmail(email);

        // 예외처리 : 중복 이메일 검사
        if (userFound.isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        }

        Users users = userRepository.save(userSignUpRequestDto.toEntity());

        return users.getId();
    }

    public void deleteUser(Long userId) {

        Optional<Users> userFound = userRepository.findById(userId);

        // 예외처리 : 존재하지 않는 데이터 검사
        if (userFound.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUND);
        }

        userRepository.delete(userFound.get());
    }

    // 이메일 기반 로그인은 사용하지 않음
    @Override
    public LoginDto signInUser(String authorizationCode) {
        return null;
    }
}
