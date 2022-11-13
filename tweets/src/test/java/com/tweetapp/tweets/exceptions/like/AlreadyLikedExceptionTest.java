package com.tweetapp.tweets.exceptions.like;

import com.tweetapp.tweets.exception.like.AlreadyLikedException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AlreadyLikedExceptionTest {
    private AlreadyLikedException e = new AlreadyLikedException("message");

    @Test
    void testMessageSetter() {
        assertThat(e).isNotNull();
    }
}
