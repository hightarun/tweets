package com.tweetapp.tweets.exceptions.authentication;

import com.tweetapp.tweets.exception.authentication.EmailDoesNotExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class EmailDoesNotExistExceptionTest {
    private EmailDoesNotExistsException e = new EmailDoesNotExistsException("message");

    @Test
    void testMessageSetter() {
        assertThat(e).isNotNull();
    }
}
