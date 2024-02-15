package com.haru4cut.domain.user;

import com.haru4cut.global.exception.CustomException;
import com.haru4cut.global.exception.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Test
    public void testSaveUser_success() throws Exception {
        //given
        Users mock = mock(Users.class);
        given(mock.getId()).willReturn(1l);
        given(userRepository.save(Mockito.any(Users.class))).willReturn(mock);

        //when
        Long userId = userService.createUser(new UserSignUpRequestDto("email", "password"));

        //then
        assertThat(userId).isEqualTo(1l);
    }

    @Test
    public void testSaveUser_DUPLICATERESOURCE() throws Exception {
        //given
        String email = "test";
        String password = "password";
        given(userRepository.findByEmail(email)).willThrow(new CustomException(ErrorCode.DUPLICATE_RESOURCE));

        //when, then
        Assertions.assertThatThrownBy(()-> userService.createUser(new UserSignUpRequestDto(email, password))).isInstanceOf(CustomException.class).hasFieldOrProperty("errorCode");
    }

    @Test
    public void testDeleteUser_success() throws Exception {
        //given
        Users mock = mock(Users.class);
        given(userRepository.findById(1l)).willReturn(Optional.of(mock));
        doNothing().when(userRepository).delete(mock);

        //when
        userService.deleteUser(1l);

        //then
        verify(userRepository).findById(1l);
        verify(userRepository).delete(mock);
    }
    
    @Test
    public void testDeleteUser_NOTFOUND() throws Exception {
        //given
        doReturn(Optional.empty()).when(userRepository).findById(1l);

        //when, then
        assertThatThrownBy(()->userService.deleteUser(1l)).isInstanceOf(CustomException.class).hasFieldOrProperty("errorCode");

    }
}