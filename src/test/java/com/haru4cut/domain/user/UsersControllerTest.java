package com.haru4cut.domain.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.haru4cut.global.exception.CustomException;
import com.haru4cut.global.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UsersControllerTest {

    private static final Long USER_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    public void testSignUp_success() throws Exception {
        //given
        UserSignUpRequestDto userRequestDto = new UserSignUpRequestDto("test@example.com", "testPassword");
        given(userService.createUser(Mockito.any(UserSignUpRequestDto.class))).willReturn(USER_ID);

        //when
        mockMvc.perform(
                post("/users")
                        .content(new ObjectMapper().writeValueAsString(userRequestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").exists())
                .andDo(print());

        //then
        verify(userService).createUser(userRequestDto);
    }
    
    @Test
    public void testSignUp_DUPLICATERESOURCE() throws Exception {
        //given
        given(userService.createUser(Mockito.any(UserSignUpRequestDto.class))).willThrow(new CustomException(ErrorCode.DUPLICATE_RESOURCE));

        //when
        mockMvc.perform(
                        post("/users")
                                .content(new ObjectMapper().writeValueAsString(new UserSignUpRequestDto()))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andDo(print());

        //then
    }

    @Test
    public void testSignUp_BADREQUEST() throws Exception {
        //given
        given(userService.createUser(Mockito.any(UserSignUpRequestDto.class))).willReturn(USER_ID);

        //when
        mockMvc.perform(
                        post("/users")
                                .content(new ObjectMapper().writeValueAsString(new UserSignUpRequestDto("wrongEmail", "password")))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());

        //then
    }

    @Test
    public void testDelete_success() throws Exception {
        //given
        doNothing().when(userService).deleteUser(USER_ID);

        //when
        mockMvc.perform(
                        delete("/users/delete")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\": 1}"))
                .andExpect(status().isOk())
                .andDo(print());

        //then
    }

    @Test
    public void testDelete_NOTFOUND() throws Exception {
        //given
        doThrow(new CustomException(ErrorCode.NOT_FOUND)).when(userService).deleteUser(USER_ID);

        //when
        mockMvc.perform(
                        delete("/users/delete")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"userId\": 1}"))
                .andExpect(status().isNotFound())
                .andDo(print());

        //then

    }
    
}