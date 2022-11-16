package com.tweetapp.tweets.model.comment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommentRequestTest {

    private CommentRequest commentRequest;

    @Test
    void test(){
        commentRequest = new CommentRequest("amazing!");
        Assertions.assertEquals(commentRequest.getContent() , "amazing!");
    }

    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(CommentRequest.class);
    }
}
