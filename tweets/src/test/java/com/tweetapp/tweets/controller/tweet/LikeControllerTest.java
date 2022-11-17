package com.tweetapp.tweets.controller.tweet;


import com.tweetapp.tweets.exception.authentication.AuthorizationException;
import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.tweet.Tweet;
import com.tweetapp.tweets.model.tweet.TweetRequest;
import com.tweetapp.tweets.service.like.LikeServiceImpl;
import com.tweetapp.tweets.util.AuthenticationHelper;
import com.tweetapp.tweets.util.UserHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class LikeControllerTest {

    private LikeController likeController;
    private LikeServiceImpl likeService;
    private UserHelper userHelper;
    private AuthenticationHelper authenticationHelper;
    private AuthenticationManager authenticationManager;
    private User user;

    @BeforeEach
    void setUp() {
        likeService = Mockito.mock(LikeServiceImpl.class);
        userHelper = Mockito.mock(UserHelper.class);
        authenticationHelper = Mockito.mock(AuthenticationHelper.class);
        likeController = new LikeController(likeService, userHelper, authenticationHelper);
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        user = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
    }

    @Test
    public void addLikeTest() throws AuthorizationException, TweetNotFoundException {
        TweetRequest tweetRequest = new TweetRequest("Hello World!");
        Tweet tweet = new Tweet(1L, tweetRequest.getContent(), user, new Date(), null);
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        when(authenticationHelper.authorizeRequest("token")).thenReturn(true);
        when(likeService.addLike(tweet.getId(), "token")).thenReturn("hightarun");
        assertThat(likeController.addLike(user.getUsername(), tweet.getId(), "token"));
    }

    @Test
    public void addLikeShouldThrowException() {
        TweetRequest tweetRequest = new TweetRequest("Hello World!");
        Tweet tweet = new Tweet(1L, tweetRequest.getContent(), user, new Date(), null);
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        when(authenticationHelper.authorizeRequest("token1")).thenReturn(false);
        assertThatThrownBy(() -> likeController.addLike(user.getUsername(), tweet.getId(), "token"))
                .isInstanceOf(AuthorizationException.class).hasMessage("UNAUTHORIZED_REQUEST");
    }

    @Test
    public void deleteLike() throws AuthorizationException, TweetNotFoundException {
        TweetRequest tweetRequest = new TweetRequest("Hello World!");
        Tweet tweet = new Tweet(1L, tweetRequest.getContent(), user, new Date(), null);
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        when(authenticationHelper.authorizeRequest("token")).thenReturn(true);
        when(likeService.deleteLike(tweet.getId(), "token")).thenReturn("hightarun");
        assertThat(likeController.deleteLike(user.getUsername(), tweet.getId(), "token")).isNotNull();
    }

    @Test
    public void deleteLikeShouldThrowException() {
        TweetRequest tweetRequest = new TweetRequest("Hello World!");
        Tweet tweet = new Tweet(1L, tweetRequest.getContent(), user, new Date(), null);
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        when(authenticationHelper.authorizeRequest("token1")).thenReturn(false);
        assertThatThrownBy(() -> likeController.deleteLike(user.getUsername(), tweet.getId(), "token"))
                .isInstanceOf(AuthorizationException.class).hasMessage("UNAUTHORIZED_REQUEST");
    }
}
