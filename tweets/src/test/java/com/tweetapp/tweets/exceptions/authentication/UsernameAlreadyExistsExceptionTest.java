package com.tweetapp.tweets.exceptions.authentication;

import com.tweetapp.tweets.exception.authentication.UsernameAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UsernameAlreadyExistsExceptionTest {
    private UsernameAlreadyExistsException e = new UsernameAlreadyExistsException("message");

    @Test
    void testMessageSetter() {
        assertThat(e).isNotNull();
    }
}
