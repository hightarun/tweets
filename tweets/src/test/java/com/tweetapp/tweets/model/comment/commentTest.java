package com.tweetapp.tweets.model.comment;

import com.tweetapp.tweets.model.tweet.Tweet;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class commentTest {

    private Comment comment;

    private Comment comment1;

    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(Comment.class);
    }

}
