package com.tweetapp.tweets.model.tweet;

import com.tweetapp.tweets.model.comment.Comment;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TweetUserTest {

    private TweetUser tweetUser;

    private TweetUser tweetUser2;

    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(TweetUser.class);
    }
}
