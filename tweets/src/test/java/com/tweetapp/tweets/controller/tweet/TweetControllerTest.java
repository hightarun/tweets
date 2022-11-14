package com.tweetapp.tweets.controller.tweet;

import com.tweetapp.tweets.service.tweet.TweetServiceImpl;
import com.tweetapp.tweets.util.AuthenticationHelper;
import com.tweetapp.tweets.util.UserHelper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public class TweetControllerTest {

    private TweetController tweetController;
    private UserHelper userHelper;
    private AuthenticationHelper authenticationHelper;
    private TweetServiceImpl tweetService;

    @BeforeEach
    void setUp() {
        tweetController = Mockito.mock(TweetController.class);

        userHelper = Mockito.mock(UserHelper.class);
        authenticationHelper = Mockito.mock(AuthenticationHelper.class);
        tweetService = Mockito.mock(TweetServiceImpl.class);
        tweetController = new TweetController(tweetService, userHelper, authenticationHelper);
    }
}
