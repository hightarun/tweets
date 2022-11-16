package com.tweetapp.tweets.util;

import com.tweetapp.tweets.config.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class UserHelperTest {

    private UserHelper userHelper;

    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {
        this.jwtTokenUtil = Mockito.mock(JwtTokenUtil.class);
        userHelper = new UserHelper(jwtTokenUtil);
    }

    @Test
    void getTokenFromRequestHeaderTest(){
        String token = "Bearer sjadjkasijsfa";

        assertThat(userHelper.getTokenFromRequestHeader(token)).isNotNull();
    }

    @Test
    void getUsernameFromRequestHeaderTest(){
        String token = "Bearer fkklkdmkmldkmvfl";
        String username = "hightarun";
        Mockito.when(jwtTokenUtil.getUsernameFromToken("token")).thenReturn(username);
        assertThat(userHelper.getUsernameFromRequestHeader(token)).isNull();
    }
}
