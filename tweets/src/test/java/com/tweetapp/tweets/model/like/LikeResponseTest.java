package com.tweetapp.tweets.model.like;

import com.tweetapp.tweets.model.comment.Comment;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LikeResponseTest {
    private LikeResponse likeResponse;

    private  LikeResponse likeResponse2;

    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(LikeResponse.class);
    }
}
