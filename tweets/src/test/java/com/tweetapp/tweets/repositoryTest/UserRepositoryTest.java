package com.tweetapp.tweets.repositoryTest;

import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.repository.UserRepository;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    public void testFindByUserName() {
        User u = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "123456789", "8929409364", null);
        Mockito.when(userRepository.findByUsername("hightarun")).thenReturn(u);
        assertThat(userRepository.findByUsername("hightarun").equals(u)).isNotNull();
    }
}
