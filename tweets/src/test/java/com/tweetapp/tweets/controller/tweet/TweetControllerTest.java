package com.tweetapp.tweets.controller.tweet;

import com.tweetapp.tweets.exception.authentication.AuthorizationException;
import com.tweetapp.tweets.exception.authentication.UsernameNotExistsException;
import com.tweetapp.tweets.exception.tweet.TweetNotAuthorizedException;
import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.authentication.UserDetailsResponse;
import com.tweetapp.tweets.model.comment.CommentResponse;
import com.tweetapp.tweets.model.like.LikeResponse;
import com.tweetapp.tweets.model.tweet.Tweet;
import com.tweetapp.tweets.model.tweet.TweetRequest;
import com.tweetapp.tweets.model.tweet.TweetResponse;
import com.tweetapp.tweets.model.tweet.TweetUser;
import com.tweetapp.tweets.repository.UserRepository;
import com.tweetapp.tweets.service.tweet.TweetServiceImpl;
import com.tweetapp.tweets.util.AuthenticationHelper;
import com.tweetapp.tweets.util.UserHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class TweetControllerTest {

    private TweetController tweetController;
    private UserHelper userHelper;
    private AuthenticationHelper authenticationHelper;
    private TweetServiceImpl tweetService;

    private UserRepository userRepository;

    private AuthenticationManager authenticationManager;

    private UserDetailsResponse userDetailsResponse;

    private User user;

    @BeforeEach
    void setUp() {
        tweetController = Mockito.mock(TweetController.class);
        userRepository = Mockito.mock(UserRepository.class);
        userHelper = Mockito.mock(UserHelper.class);
        authenticationHelper = Mockito.mock(AuthenticationHelper.class);
        tweetService = Mockito.mock(TweetServiceImpl.class);
        tweetController = new TweetController(tweetService, userHelper, authenticationHelper);
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        userDetailsResponse = new UserDetailsResponse(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun");
        user = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
    }

    @Test
    public void addTweet() throws AuthorizationException {
        TweetRequest tweetRequest = new TweetRequest("Hello World!");
        Tweet tweet = new Tweet(1L, tweetRequest.getContent(), user, new Date(), null);
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        when(authenticationHelper.authorizeRequest("token")).thenReturn(true);
        when(tweetService.addTweets(tweetRequest, "token")).thenReturn("hightarun");
        assertThat(tweetController.addTweet(user.getUsername(), tweetRequest, "token"));
    }

    @Test
    public void addTweetShouldThrowException() {
        TweetRequest tweetRequest = new TweetRequest("Hello World!");
        Tweet tweet = new Tweet(1L, tweetRequest.getContent(), user, new Date(), null);
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        when(authenticationHelper.authorizeRequest("token1")).thenReturn(false);
        assertThatThrownBy(() -> tweetController.addTweet(user.getUsername(), tweetRequest, "token"))
                .isInstanceOf(AuthorizationException.class).hasMessage("UNAUTHORIZED_REQUEST");
    }

    @Test
    public void updateTweetTest() throws AuthorizationException, UsernameNotExistsException, TweetNotAuthorizedException, TweetNotFoundException {
        TweetRequest tweetRequest = new TweetRequest("Hello World!");
        Tweet tweet = new Tweet(1L, tweetRequest.getContent(), user, new Date(), null);
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        when(authenticationHelper.authorizeRequest("token")).thenReturn(true);
        when(tweetService.updateTweets(tweetRequest, "token", 1L)).thenReturn("hightarun");
        assertThat(tweetController.updateTweet(user.getUsername(), 1L, tweetRequest, "token"));
    }

    @Test
    public void updateTweetShouldThrowException() {
        TweetRequest tweetRequest = new TweetRequest("Hello World!");
        Tweet tweet = new Tweet(1L, tweetRequest.getContent(), user, new Date(), null);
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        when(authenticationHelper.authorizeRequest("token1")).thenReturn(false);
        assertThatThrownBy(() -> tweetController.updateTweet(user.getUsername(), 1L, tweetRequest, "token"))
                .isInstanceOf(AuthorizationException.class).hasMessage("UNAUTHORIZED_REQUEST");
    }

    @Test
    public void getAllTweetUser() {
        TweetUser tweetUser = new TweetUser(1L, "Tarun", "Bisht", "hightarun");
        TweetRequest tweetRequest = new TweetRequest("Hello World!");
        CommentResponse commentResponse = new CommentResponse(1L, "hey", userDetailsResponse, 1L);
        List<CommentResponse> commentResponses = new ArrayList<>();
        commentResponses.add(commentResponse);
        LikeResponse likeResponse = new LikeResponse(1L, user.getId(), 1L);
        List<LikeResponse> likeResponses = new ArrayList<>();
        likeResponses.add(likeResponse);
        TweetResponse tweetResponse = new TweetResponse(1L, "Hey!", tweetUser, commentResponses, likeResponses, null, null);
        List<TweetResponse> tweetResponses = new ArrayList<>();
        tweetResponses.add(tweetResponse);
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        when(authenticationHelper.authorizeRequest("token")).thenReturn(true);
        when(tweetService.getAllTweetsUser(user.getUsername())).thenReturn(tweetResponses);
        assertThat(tweetController.getALlTweetsUser("hightarun")).isNotNull();
    }

    @Test
    public void deleteTweets() throws AuthorizationException, UsernameNotExistsException, TweetNotAuthorizedException, TweetNotFoundException {
        TweetRequest tweetRequest = new TweetRequest("Hello World!");
        Tweet tweet = new Tweet(1L, tweetRequest.getContent(), user, new Date(), null);
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        when(authenticationHelper.authorizeRequest("token")).thenReturn(true);
        when(tweetService.deleteTweet("token", tweet.getId())).thenReturn("deleted");
        assertThat(tweetController.deleteTweet(user.getUsername(), 1L, "token")).isNotNull();
    }

    @Test
    public void getAllTweets() {
        TweetUser tweetUser = new TweetUser(1L, "Tarun", "Bisht", "hightarun");
        CommentResponse commentResponse = new CommentResponse(1L, "hey", userDetailsResponse, 1L);
        List<CommentResponse> commentResponses = new ArrayList<>();
        commentResponses.add(commentResponse);
        LikeResponse likeResponse = new LikeResponse(1L, user.getId(), 1L);
        List<LikeResponse> likeResponses = new ArrayList<>();
        likeResponses.add(likeResponse);
        TweetResponse tweetResponse = new TweetResponse(1L, "Hey!", tweetUser, commentResponses, likeResponses, null, null);
        List<TweetResponse> tweetResponses = new ArrayList<>();
        tweetResponses.add(tweetResponse);
        when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        when(authenticationHelper.authorizeRequest("token")).thenReturn(true);
        when(tweetService.getAllTweet()).thenReturn(tweetResponses);
        assertThat(tweetController.getAllTweet()).isNotNull();
    }

    @Test
    public void deleteTweetShouldThrowAnException() {
        TweetRequest tweetRequest = new TweetRequest("Hello World!");
        Tweet tweet = new Tweet(1L, tweetRequest.getContent(), user, new Date(), null);
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        when(authenticationHelper.authorizeRequest("token1")).thenReturn(false);
        assertThatThrownBy(() -> tweetController.deleteTweet(user.getUsername(), 1L, "token"))
                .isInstanceOf(AuthorizationException.class).hasMessage("UNAUTHORIZED_REQUEST");
    }
}
