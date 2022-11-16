package com.tweetapp.tweets.model.like;

import org.junit.jupiter.api.Assertions;
import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.tweet.Tweet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LikeTest {
    private Like like;

    private User user;

    private Tweet tweet;

    @BeforeEach
    void setUp(){
        user = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "123456789", "8929409364", null);
        tweet = new Tweet(1L , "hello World!" , user ,null , null);
    }

    @Test
    void test(){
        like = new Like(1L , null , user ,tweet);
        Assertions.assertEquals(like.getId() , 1L);
        Assertions.assertEquals(like.getCreateTime() , null);
        Assertions.assertEquals(like.getUser() , user);
        Assertions.assertEquals(like.getTweet() , tweet);
    }
    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(Like.class);
    }
}
