package com.tweetapp.tweets.model.comment;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentResponseTest {
    private CommentResponse commentResponse;

    private CommentResponse commentResponse2;

    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(CommentResponse.class);
    }
}
