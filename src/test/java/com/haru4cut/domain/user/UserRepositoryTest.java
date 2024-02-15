package com.haru4cut.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    static final String email = "testEmail";
    static final String password = "testPassword";

    @Autowired
    UserRepository userRepository;
    
    @Test
    public void testSave() throws Exception {
        //given
        Users user = new Users(email, password);

        //when
        userRepository.save(user);

        //then
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getPassword()).isEqualTo(password);
    }

    @Test
    public void testFind() throws Exception {
        //given
        Users user = new Users(email, password);
        userRepository.save(user);

        //when
        Users userFound = userRepository.findById(1l).get();

        //then
        assertThat(userFound).isEqualTo(user);
        assertThat(userFound.getId()).isEqualTo(1l);
    }

    @Test
    public void testDelete() throws Exception {
        //given
        Users user = new Users(email, password);
        userRepository.save(user);

        //when
        userRepository.delete(user);

        //then
        assertThat(userRepository.findById(user.getId())).isEmpty();

    }

}