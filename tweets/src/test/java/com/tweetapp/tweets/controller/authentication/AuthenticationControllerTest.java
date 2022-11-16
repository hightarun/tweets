package com.tweetapp.tweets.controller.authentication;

import com.tweetapp.tweets.config.JwtTokenUtil;
import com.tweetapp.tweets.exception.TryCatchException;
import com.tweetapp.tweets.exception.authentication.AuthorizationException;
import com.tweetapp.tweets.exception.authentication.UsernameAlreadyExistsException;
import com.tweetapp.tweets.exception.authentication.UsernameNotExistsException;
import com.tweetapp.tweets.model.authentication.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.tweetapp.tweets.service.authentication.JwtUserDetailsServiceImpl;
import com.tweetapp.tweets.util.AuthenticationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class AuthenticationControllerTest {

    private AuthenticationController authenticationController;

    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsServiceImpl jwtUserDetailsService;
    private AuthenticationHelper authenticationHelper;
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() throws Exception {
        jwtTokenUtil = Mockito.mock(JwtTokenUtil.class);
        jwtUserDetailsService = Mockito.mock(JwtUserDetailsServiceImpl.class);
        authenticationHelper = Mockito.mock(AuthenticationHelper.class);
        authenticationController = new AuthenticationController(jwtTokenUtil, jwtUserDetailsService, authenticationHelper);
        this.authenticationManager = Mockito.mock(AuthenticationManager.class);
    }

    @Test
    void testGenerateToken() throws AuthorizationException {
        JwtLoginRequest jwtLoginRequest = new JwtLoginRequest("hightarun", "12345678");
        User u1 = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(u1.getUsername(), u1.getPassword(), new ArrayList<>());
        Mockito.when(jwtUserDetailsService.loadUserByUsername("hightarun")).thenReturn(userDetails);
        Mockito.when(jwtTokenUtil.generateToken(userDetails)).thenReturn("token");
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(u1.getUsername(), u1.getPassword()))).thenReturn(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return true;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return null;
            }
        });
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(u1.getUsername(), u1.getPassword()));
        Mockito.when(authenticationHelper.authenticate(u1.getUsername(), u1.getPassword())).thenReturn(auth);
        ResponseEntity<?> entity = authenticationController.createToken(jwtLoginRequest);
        assertThat(Integer.valueOf(entity.getStatusCodeValue()).equals(Integer.valueOf(200))).isTrue().isNotNull();
    }

    @Test
    void generateTokenShouldThrowException() throws AuthorizationException {
        JwtLoginRequest jwtLoginRequest = new JwtLoginRequest("hightarun", "12345678");
        User u1 = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(u1.getUsername(), u1.getPassword(), new ArrayList<>());
        Mockito.when(jwtUserDetailsService.loadUserByUsername("hightarun")).thenReturn(userDetails);
        Mockito.when(jwtTokenUtil.generateToken(userDetails)).thenReturn("token");
        Mockito.when(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(u1.getUsername(), u1.getPassword()))).thenReturn(new Authentication() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public Object getCredentials() {
                return null;
            }

            @Override
            public Object getDetails() {
                return null;
            }

            @Override
            public Object getPrincipal() {
                return null;
            }

            @Override
            public boolean isAuthenticated() {
                return false;
            }

            @Override
            public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

            }

            @Override
            public String getName() {
                return null;
            }
        });
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(u1.getUsername(), u1.getPassword()));
        Mockito.when(authenticationHelper.authenticate(u1.getUsername(), u1.getPassword())).thenReturn(auth);
        assertThatThrownBy(() -> authenticationController.createToken(jwtLoginRequest))
                .isInstanceOf(AuthorizationException.class).hasMessage("USER NOT AUTHENTICATED");
    }

    @Test
    public void testAuthorization() {
        Mockito.when(jwtTokenUtil.getUsernameFromToken("Bearer token")).thenReturn(null);
        assertThat(authenticationController.authorizeRequest("Bearer token"));
    }

    @Test
    public void authorizationInvalid() {
        User user = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
        Mockito.when(jwtUserDetailsService.loadUserByUsername("hightarun")).thenReturn(userDetails);
        Mockito.when(jwtTokenUtil.getUsernameFromToken("token")).thenReturn("hightarun");
        assertThat(authenticationController.authorizeRequest("token"));
    }

    @Test
    public void testAuthorizationNullUser() throws Exception {

        User user = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
        UserDetails details = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
        when(jwtUserDetailsService.loadUserByUsername("hightarun")).thenReturn(details);
        when(jwtTokenUtil.getUsernameFromToken("token")).thenReturn("hightarun");

        assertThat(authenticationController.authorizeRequest("token"));

    }

    @Test
    public void forgotPassword() throws Exception {
        User user = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
        when(authenticationController.forgotPassword(user.getUsername())).thenReturn("hightarun");
        assertThat(authenticationController.forgotPassword(user.getUsername()));
    }

    @Test
    public void registerUser() throws Exception {
        User user = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
        JwtRegisterRequest jwtRegisterRequest = new JwtRegisterRequest("Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "892940964");
        when(jwtUserDetailsService.addUser(jwtRegisterRequest)).thenReturn("hightarun");
        assertThat(authenticationController.registerUser(jwtRegisterRequest)).isNotNull();
    }

    @Test
    public void resetPasswordTest() throws Exception {

        Random random = new Random();
        int number = random.nextInt(999999);
        String code = String.format("%06d", number);
        User user = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", code);
        ResetPassword resetPass = new ResetPassword(code, "hightarun", "12345678");
        when(jwtUserDetailsService.resetPassword(resetPass)).thenReturn(user.getUsername());
        assertThat(authenticationController.resetPassword(resetPass)).isNotNull();
    }

    @Test
    public void getAllUsersTest() throws Exception {
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun");
        List<UserDetailsResponse> userDetailsResponses = new ArrayList<>();
        userDetailsResponses.add(userDetailsResponse);
        when(jwtUserDetailsService.getAllUser()).thenReturn(userDetailsResponses);
        assertThat(authenticationController.getAllUsers()).isNotNull();
    }

    @Test
    public void searchByUsernameTest() throws UsernameNotExistsException {
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun");
        when(jwtUserDetailsService.searchUserByUsername(userDetailsResponse.getUsername())).thenReturn(userDetailsResponse);
        assertThat(authenticationController.searchUserByUsername(userDetailsResponse.getUsername())).isNotNull();
    }


}
