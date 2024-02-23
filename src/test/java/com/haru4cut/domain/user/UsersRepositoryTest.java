package com.haru4cut.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UsersRepositoryTest {

    static final String email = "testEmail";
    static final String password = "testPassword";

    @Autowired
    UserRepository userRepository;
    
    @Test
    public void testSave() throws Exception {
        //given
        Users users = new Users(email, password);

        //when
        userRepository.save(users);

        //then
        assertThat(users.getEmail()).isEqualTo(email);
        assertThat(users.getPassword()).isEqualTo(password);
    }

    @Test
    public void testFind() throws Exception {
        //given
        Users users = new Users(email, password);
        userRepository.save(users);

        //when
        Users usersFound = userRepository.findById(1l).get();

        //then
        assertThat(usersFound).isEqualTo(users);
        assertThat(usersFound.getId()).isEqualTo(1l);
    }

    @Test
    public void testDelete() throws Exception {
        //given
        Users users = new Users(email, password);
        userRepository.save(users);

        //when
        userRepository.delete(users);

        //then
        assertThat(userRepository.findById(users.getId())).isEmpty();

    }

}