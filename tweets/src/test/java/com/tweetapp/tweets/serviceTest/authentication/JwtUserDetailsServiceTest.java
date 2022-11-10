package com.tweetapp.tweets.serviceTest.authentication;

import com.tweetapp.tweets.exception.authentication.UsernameNotExistsException;
import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.authentication.UserDetailsResponse;
import com.tweetapp.tweets.repository.UserRepository;
import com.tweetapp.tweets.service.authentication.JwtUserDetailsServiceImpl;
import com.tweetapp.tweets.util.DtoConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
public class JwtUserDetailsServiceTest {

    @InjectMocks
    private JwtUserDetailsServiceImpl service;

    @Mock
    private PasswordEncoder bcryptEncoder;

    @Mock
    private DtoConverter dtoConverter;

    @Mock
    private UserRepository repository;

    @BeforeEach
    void setUp() throws Exception {
    }

    @Test
    void loadUserByUserNameShouldThrowExceptionTest() {
        when(repository.findByUsername("megha")).thenReturn(null);
        assertThatThrownBy(() -> service.loadUserByUsername("megha"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found with username megha");
        verify(repository, Mockito.times(1)).findByUsername("megha");
    }

    @Test
    void loadUserByUserName() {
        when(repository.findByUsername("hightarun")).thenReturn(new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null));
        assertThat(service.loadUserByUsername("hightarun")).isNotNull();
        verify(repository, Mockito.times(1)).findByUsername("hightarun");
    }


    @Test
    void searchUserByUsername() throws UsernameNotExistsException {
        when(repository.findByUsername("hightarun")).thenReturn(new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null));
        assertThat(service.searchUserByUsername("hightarun"));
        verify(repository, Mockito.times(1)).findByUsername("hightarun");
    }

    @Test
    void searchUserByUsernameShouldThrowExceptionTest() {
        when(repository.findByUsername("megha")).thenReturn(null);
        assertThatThrownBy(() -> service.searchUserByUsername("megha"))
                .isInstanceOf(UsernameNotExistsException.class)
                .hasMessage("User with username megha does not exists");
        verify(repository, Mockito.times(1)).findByUsername("megha");
    }

    @Test
    void addUser() {
        User user = new User(2L, "Megha", "Samant", "samant19@gmail.com", "megha", "123456870", "9654313554", null);
        when(repository.save(user)).thenReturn(user);
    }

    @Test
    void getAllUser() {
        User u1 = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
        ;
        List<User> users = new ArrayList<>();
        users.add(u1);
        when(repository.findAll()).thenReturn(users);
    }
}
