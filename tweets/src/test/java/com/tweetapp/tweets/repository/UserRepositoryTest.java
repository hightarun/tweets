package com.tweetapp.tweets.repository;

import com.tweetapp.tweets.model.authentication.User;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

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
