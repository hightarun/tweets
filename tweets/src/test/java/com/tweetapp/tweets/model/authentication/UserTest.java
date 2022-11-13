package com.tweetapp.tweets.model.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.meanbean.test.BeanTester;

@SpringBootTest
public class UserTest {

    private User user1;

    private User user2;

    @BeforeEach
    void setUp() throws Exception {

        user1 = new User();
        user2 = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "123456789", "8929409364", null);
    }

    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(User.class);
    }
}
