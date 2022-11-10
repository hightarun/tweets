package com.tweetapp.tweets.exceptionsTest.comment;

import com.tweetapp.tweets.exception.comment.CommentNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CommentNotFoundTest {
    private CommentNotFoundException e = new CommentNotFoundException("message");

    @Test
    void tesMessageSetter(){
        assertThat(e).isNotNull();
    }
}
