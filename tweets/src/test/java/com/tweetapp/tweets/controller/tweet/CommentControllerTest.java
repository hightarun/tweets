package com.tweetapp.tweets.controller.tweet;

import com.tweetapp.tweets.controller.authentication.AuthenticationController;
import com.tweetapp.tweets.service.comment.CommentServiceImpl;
import com.tweetapp.tweets.util.AuthenticationHelper;
import com.tweetapp.tweets.util.UserHelper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public class CommentControllerTest {

    private CommentController commentController;
    private CommentServiceImpl commentService;
    private UserHelper userHelper;
    private AuthenticationHelper authenticationHelper;

    @BeforeEach
    void setUp() {
        commentService = Mockito.mock(CommentServiceImpl.class);
        userHelper = Mockito.mock(UserHelper.class);
        authenticationHelper = Mockito.mock(AuthenticationHelper.class);
        commentController = new CommentController(commentService, userHelper, authenticationHelper);
    }
}
