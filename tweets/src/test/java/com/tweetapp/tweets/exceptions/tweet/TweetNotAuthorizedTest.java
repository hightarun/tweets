package com.tweetapp.tweets.exceptions.tweet;

import com.tweetapp.tweets.exception.tweet.TweetNotAuthorizedException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TweetNotAuthorizedTest {
    private TweetNotAuthorizedException e = new TweetNotAuthorizedException("message");

    @Test
    void testMessageSetter() {
        assertThat(e).isNotNull();
    }
}
