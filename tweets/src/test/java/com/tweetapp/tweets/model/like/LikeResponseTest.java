package com.tweetapp.tweets.model.like;


import org.junit.jupiter.api.Assertions;
import com.tweetapp.tweets.model.tweet.TweetUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LikeResponseTest {
    private LikeResponse likeResponse;

    private TweetUser tweetUser;

    @BeforeEach
    void setUp(){
        tweetUser = new TweetUser(1L , "Tarun" , "Bisht" , "hightarun");
    }

    @Test
    void test(){

        likeResponse = new LikeResponse(1L , 1L , tweetUser.getUser_id() );
        Assertions.assertEquals(likeResponse.getId() , 1L);
        Assertions.assertEquals(likeResponse.getLikeTweetId() , 1L);
        Assertions.assertEquals(likeResponse.getLikeTweetId() , tweetUser.getUser_id());
    }
    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(LikeResponse.class);
    }
}
