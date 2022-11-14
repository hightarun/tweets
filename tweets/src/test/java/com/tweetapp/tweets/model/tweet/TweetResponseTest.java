package com.tweetapp.tweets.model.tweet;

import com.tweetapp.tweets.model.comment.Comment;
import com.tweetapp.tweets.model.tweet.TweetResponse;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TweetResponseTest {

    private TweetResponse tweetResponse;

    private TweetResponse tweetResponse2;

    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(TweetResponse.class);
    }
}
