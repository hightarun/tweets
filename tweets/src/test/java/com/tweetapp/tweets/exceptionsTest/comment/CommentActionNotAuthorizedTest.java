package com.tweetapp.tweets.exceptionsTest.comment;

import com.tweetapp.tweets.exception.comment.CommentActionNotAuthorized;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CommentActionNotAuthorizedTest {

    private CommentActionNotAuthorized e = new CommentActionNotAuthorized("message");

    @Test
    void testMessageSetter(){assertThat(e).isNotNull();}
}
