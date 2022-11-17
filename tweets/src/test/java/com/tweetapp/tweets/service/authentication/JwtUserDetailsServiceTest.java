package com.tweetapp.tweets.service.authentication;


import com.tweetapp.tweets.exception.authentication.UsernameAlreadyExistsException;
import com.tweetapp.tweets.exception.authentication.UsernameNotExistsException;
import com.tweetapp.tweets.model.authentication.JwtRegisterRequest;
import com.tweetapp.tweets.model.authentication.ResetPassword;
import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.repository.UserRepository;
import com.tweetapp.tweets.util.DtoConverter;
import com.tweetapp.tweets.util.EmailSenderService;
import com.tweetapp.tweets.util.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.mockito.Mockito.*;


@SpringBootTest
class JwtUserDetailsServiceTest {

    private JwtUserDetailsServiceImpl service;
    private PasswordEncoder passwordEncoder;
    private DtoConverter dtoConverter;
    private EmailSenderService emailSenderService;
    private UserRepository repository;

    private User u;

    @BeforeEach
    void setUp() throws Exception {
        repository = Mockito.mock(UserRepository.class);
        dtoConverter = Mockito.mock(DtoConverter.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        emailSenderService = Mockito.mock(EmailSenderService.class);
        service = new JwtUserDetailsServiceImpl(repository, passwordEncoder, dtoConverter, emailSenderService);
        u = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", "123547");
    }

    @Test
    void passwordEncoderNotNull() {
        assertThat(passwordEncoder).isNotNull();
    }

    @Test
    void serviceNotNull() {
        assertThat(service).isNotNull();
    }

    @Test
    void repositoryNotNUll() {
        assertThat(repository).isNotNull();
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
        User u1 = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
        when(repository.findByUsername("hightarun")).thenReturn(new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null));
        assertThat(service.searchUserByUsername("hightarun")).isNull();
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
    void addUserShouldThrowException() {
        User u1 = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
        JwtRegisterRequest jwtRegisterRequest = new JwtRegisterRequest();
        jwtRegisterRequest.setFirstName("Tarun");
        jwtRegisterRequest.setLastName("Bisht");
        jwtRegisterRequest.setUsername("hightarun");
        jwtRegisterRequest.setEmail("tarun@gmail.com");
        jwtRegisterRequest.setPassword("12345678");
        jwtRegisterRequest.setContactNumber("8929409262");
        when(passwordEncoder.encoder()).thenReturn(new BCryptPasswordEncoder());
        when(repository.findByUsername("hightarun")).thenReturn(u1);
        assertThatThrownBy(() -> service.addUser(jwtRegisterRequest))
                .isInstanceOf(UsernameAlreadyExistsException.class).hasMessage("Username hightarun already exists.");
        verify(repository, Mockito.times(1)).findByUsername("hightarun");
    }


    @Test
    void addUser() throws Exception {

        JwtRegisterRequest jwtRegisterRequest = new JwtRegisterRequest();
        jwtRegisterRequest.setFirstName("Tarun");
        jwtRegisterRequest.setLastName("Bisht");
        jwtRegisterRequest.setUsername("hightarun");
        jwtRegisterRequest.setEmail("tarun@gmail.com");
        jwtRegisterRequest.setPassword("12345678");
        jwtRegisterRequest.setContactNumber("8929409262");
        when(passwordEncoder.encoder()).thenReturn(new BCryptPasswordEncoder());
        User user = new User();
        user.setId(1L);
        user.setPassword(passwordEncoder.encoder().encode(jwtRegisterRequest.getPassword()));
        user.setFirstName(jwtRegisterRequest.getFirstName());
        user.setLastName(jwtRegisterRequest.getLastName());
        user.setEmail(jwtRegisterRequest.getEmail());
        user.setUsername(jwtRegisterRequest.getUsername());
        user.setContactNumber(jwtRegisterRequest.getContactNumber());
        user.setResetCode(null);
        when(repository.save(user)).thenReturn(user);
        assertThat(service.addUser(jwtRegisterRequest)).isNotNull();
    }

    @Test
    void getAllUser() throws Exception {
        User u1 = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
        ;
        List<User> users = new ArrayList<>();
        users.add(u1);
        when(repository.findAll()).thenReturn(users);
        assertThat(service.getAllUser()).isNotNull();
    }

    @Test
    void forgotPasswordShouldThrowException() {
        when(repository.findByUsername("megha")).thenReturn(null);
        assertThatThrownBy(() -> service.forgotPassword("megha"))
                .isInstanceOf(UsernameNotExistsException.class)
                .hasMessage("Username megha does not exists.");
        verify(repository, Mockito.times(1)).findByUsername("megha");
    }

    @Test
    void forgotPasswordShouldThrowException2() {
        when(repository.findByEmail("samantmegha@gmail.com")).thenReturn(null);
        assertThatThrownBy(() -> service.forgotPassword("megha"))
                .isInstanceOf(Exception.class).hasMessage("Username megha does not exists.");
    }

    @Test
    void forgotPassword() throws UsernameNotExistsException {
        User user = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
        Random random = new Random();
        int number = random.nextInt(999999);
        String code = String.format("%06d", number);
        user.setResetCode(code);
        when(repository.findByUsername("hightarun")).thenReturn(user);
        when(repository.save(user)).thenReturn(user);
        assertThat(service.forgotPassword("hightarun")).isNotNull();
        verify(repository, Mockito.times(1)).findByUsername("hightarun");
    }

    @Test
    void resetPasswordShouldThrowException() {
        User user = new User(1L, "megha", "samant", "megha@gmail.com", "hightarun", "12345678", "8929409364", null);
        user.setResetCode("123456");
        ResetPassword resetPass = new ResetPassword("123456", "megha", "123456789");
        ResetPassword resetPass1 = new ResetPassword("654321", "megha", "123456789");
        when(passwordEncoder.encoder()).thenReturn(new BCryptPasswordEncoder());
        when(repository.findByUsername("megha")).thenReturn(null);
        assertThatThrownBy(() -> service.resetPassword(resetPass))
                .isInstanceOf(UsernameNotExistsException.class)
                .hasMessage("Username megha does not exists.");
        verify(repository, Mockito.times(1)).findByUsername("megha");
    }

    @Test
    void resetPassword() throws Exception {
        User user = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
        Random random = new Random();
        int number = random.nextInt(999999);
        String code = String.format("%06d", number);
        user.setResetCode(code);
        ResetPassword resetPass = new ResetPassword(code, "hightarun", "123456789");
        when(passwordEncoder.encoder()).thenReturn(new BCryptPasswordEncoder());
        when(repository.findByUsername("hightarun")).thenReturn(user);
        user.setPassword(passwordEncoder.encoder().encode(resetPass.getPassword()));
        when(repository.save(user)).thenReturn(user);
        assertThat(service.resetPassword(resetPass)).isNotNull();
    }
}
