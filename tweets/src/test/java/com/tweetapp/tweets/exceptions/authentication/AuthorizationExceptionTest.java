package com.tweetapp.tweets.exceptions.authentication;

import com.tweetapp.tweets.exception.authentication.AuthorizationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AuthorizationExceptionTest {

    private AuthorizationException e = new AuthorizationException("message");

    @Test
    void testMessageSetter() {
        assertThat(e).isNotNull();
    }
}
