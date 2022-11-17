package com.tweetapp.tweets.model.comment;

import org.junit.jupiter.api.Assertions;
import com.tweetapp.tweets.model.authentication.UserDetailsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class CommentResponseTest {
    private CommentResponse commentResponse;

    private UserDetailsResponse userDetailsResponse;

    @BeforeEach
    void setUp() {
        userDetailsResponse = new UserDetailsResponse(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun");
    }

    @Test
    void test() {
        commentResponse = new CommentResponse(1L, "hey Everyone!", userDetailsResponse, 1L, new Date());
        Assertions.assertEquals(commentResponse.getId(), 1L);
        Assertions.assertEquals(commentResponse.getContent(), "hey Everyone!");
        Assertions.assertEquals(commentResponse.getCommentUser(), userDetailsResponse);
        Assertions.assertEquals(commentResponse.getCommentTweetId(), 1L);
    }

    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(CommentResponse.class);
    }
}
