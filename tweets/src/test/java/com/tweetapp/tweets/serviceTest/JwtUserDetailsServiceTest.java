package com.tweetapp.tweets.serviceTest;

import com.tweetapp.tweets.exception.authentication.UsernameNotExistsException;
import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.repository.UserRepository;
import com.tweetapp.tweets.service.authentication.JwtUserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;


@SpringBootTest
public class JwtUserDetailsServiceTest {

    @Autowired
    private JwtUserDetailsServiceImpl service;


    @MockBean
    private UserRepository repository;

    @Test
    public void loadByUsernameTest() throws UsernameNotExistsException {
        String user = "hightarun";
        when(repository.findByUsername(user)).thenReturn((User) Stream.of(new User(1L, "Tarun", "Bisht", "tarunbisht123@gmail.com", "hightarun", "12345678", "8929409354")).collect(Collectors.toList()));
        
    }
}
