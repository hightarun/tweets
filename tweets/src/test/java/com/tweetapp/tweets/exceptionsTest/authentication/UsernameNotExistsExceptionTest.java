package com.tweetapp.tweets.exceptionsTest.authentication;

import com.tweetapp.tweets.exception.authentication.UsernameNotExistsException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UsernameNotExistsExceptionTest {

    private UsernameNotExistsException e = new UsernameNotExistsException("message");

    @Test
    void testMessageSetter(){
        assertThat(e).isNotNull();
    }
}
