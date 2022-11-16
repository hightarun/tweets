package com.tweetapp.tweets.model.tweet;

import org.junit.jupiter.api.Assertions;
import com.tweetapp.tweets.model.authentication.UserDetailsResponse;
import com.tweetapp.tweets.model.comment.Comment;
import com.tweetapp.tweets.model.comment.CommentResponse;
import com.tweetapp.tweets.model.like.LikeResponse;
import com.tweetapp.tweets.model.tweet.TweetResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TweetResponseTest {

    private UserDetailsResponse userDetailsResponse;
    private TweetResponse tweetResponse;

    private CommentResponse commentResponse;

    private LikeResponse likeResponse;

    private TweetUser tweetUser;

    @BeforeEach
    void setUp(){
        tweetUser = new TweetUser(1L , "Tarun" , "Bisht" , "hightarun");
        userDetailsResponse = new UserDetailsResponse(1L,"Tarun","Bisht","tarun@gmail.com","hightarun");
        commentResponse = new CommentResponse(1L , "Hello World" , userDetailsResponse , 1L);
        likeResponse = new LikeResponse(1L , 1L , tweetUser.getUser_id());

    }

    @Test
    void test(){
        List<CommentResponse> commentResponses = new ArrayList<>();
        commentResponses.add(commentResponse);
        List<LikeResponse> likeResponses = new ArrayList<>();
        likeResponses.add(likeResponse);
        tweetResponse = new TweetResponse(1L , "hello world!" , tweetUser , commentResponses , likeResponses ,null , null);
        Assertions.assertEquals(tweetResponse.getId() , 1L);
        Assertions.assertEquals(tweetResponse.getContent() , "hello world!");
        Assertions.assertEquals(tweetResponse.getTweetUser() , tweetUser);
        Assertions.assertEquals(tweetResponse.getComments() , commentResponses);
        Assertions.assertEquals(tweetResponse.getLikes() , likeResponses);
        Assertions.assertEquals(tweetResponse.getCreateTime() , null);
        Assertions.assertEquals(tweetResponse.getUpdateTime() , null);
    }

    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(TweetResponse.class);
    }
}
