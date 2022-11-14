package com.tweetapp.tweets.model.authentication;

import com.tweetapp.tweets.model.comment.Comment;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDetailsResponseTest {
    private UserDetailsResponse userDetailsResponse1;

    private UserDetailsResponse userDetailsResponse2;

    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(UserDetailsResponse.class);
    }
}
