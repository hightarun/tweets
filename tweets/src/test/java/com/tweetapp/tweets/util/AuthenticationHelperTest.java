package com.tweetapp.tweets.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.tweetapp.tweets.config.JwtTokenUtil;
import com.tweetapp.tweets.exception.authentication.AuthorizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@SpringBootTest
public class AuthenticationHelperTest {


    private AuthenticationHelper authenticationHelper;
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {
        this.authenticationManager =Mockito.mock(AuthenticationManager.class);
        this.jwtTokenUtil = Mockito.mock(JwtTokenUtil.class);
        authenticationHelper=new AuthenticationHelper(authenticationManager,jwtTokenUtil);
    }

    @Test
    void  authenticateTest() throws AuthorizationException {
        //assertThat(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken("hightarun","12345678"))).isNotNull();
    }
}
