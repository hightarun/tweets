package com.tweetapp.tweets.controller.tweet;

import com.tweetapp.tweets.controller.authentication.AuthenticationController;
import com.tweetapp.tweets.service.like.LikeServiceImpl;
import com.tweetapp.tweets.util.AuthenticationHelper;
import com.tweetapp.tweets.util.UserHelper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public class LikeControllerTest {

    private LikeController likeController;
    private LikeServiceImpl likeService;
    private UserHelper userHelper;
    private AuthenticationHelper authenticationHelper;

    @BeforeEach
    void setUp() {
        likeService = Mockito.mock(LikeServiceImpl.class);
        userHelper = Mockito.mock(UserHelper.class);
        authenticationHelper = Mockito.mock(AuthenticationHelper.class);
        likeController = new LikeController(likeService, userHelper, authenticationHelper);
    }
}
