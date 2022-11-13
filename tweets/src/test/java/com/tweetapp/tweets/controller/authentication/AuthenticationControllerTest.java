package com.tweetapp.tweets.controller.authentication;

import com.tweetapp.tweets.config.JwtTokenUtil;
import com.tweetapp.tweets.service.authentication.JwtUserDetailsServiceImpl;
import com.tweetapp.tweets.util.AuthenticationHelper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthenticationControllerTest {

    private AuthenticationController authenticationController;

    private JwtTokenUtil jwtTokenUtil;
    private JwtUserDetailsServiceImpl jwtUserDetailsService;
    private AuthenticationHelper authenticationHelper;

    @BeforeEach
    void setUp() throws Exception {
        jwtTokenUtil = Mockito.mock(JwtTokenUtil.class);
        jwtUserDetailsService = Mockito.mock(JwtUserDetailsServiceImpl.class);
        authenticationHelper = Mockito.mock(AuthenticationHelper.class);
        authenticationController = new AuthenticationController(jwtTokenUtil, jwtUserDetailsService, authenticationHelper);
    }


}
