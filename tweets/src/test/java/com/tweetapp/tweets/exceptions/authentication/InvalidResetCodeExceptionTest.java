package com.tweetapp.tweets.exceptions.authentication;

import com.tweetapp.tweets.exception.authentication.InvalidResetCodeException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InvalidResetCodeExceptionTest {
    private InvalidResetCodeException e = new InvalidResetCodeException("message");

    @Test
    void testMessageSetter() {
        assertThat(e).isNotNull();
    }

}
