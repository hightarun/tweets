package com.tweetapp.tweets.model.tweet;

import com.tweetapp.tweets.model.authentication.User;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TweetTest {
    private Tweet tweet;

    private Tweet tweet1;

    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(Tweet.class);
    }


}
