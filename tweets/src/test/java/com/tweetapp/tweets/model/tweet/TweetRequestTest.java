package com.tweetapp.tweets.model.tweet;

import com.tweetapp.tweets.model.comment.Comment;
import com.tweetapp.tweets.model.tweet.TweetRequest;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TweetRequestTest {
    private TweetRequest tweetRequest;

    private TweetRequest tweetRequest2;

    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(TweetRequest.class);
    }
}
