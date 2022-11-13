package com.tweetapp.tweets.exceptions.tweet;

import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TweetNotFoundTest {
    private TweetNotFoundException e = new TweetNotFoundException("Message");

    @Test
    void testMessageSetter() {
        assertThat(e).isNotNull();
    }
}
