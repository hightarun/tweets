package com.tweetapp.tweets.controller.tweet;

import com.tweetapp.tweets.exception.authentication.AuthorizationException;
import com.tweetapp.tweets.exception.comment.CommentNotFoundException;
import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.comment.Comment;
import com.tweetapp.tweets.model.comment.CommentRequest;
import com.tweetapp.tweets.model.tweet.Tweet;
import com.tweetapp.tweets.model.tweet.TweetRequest;
import com.tweetapp.tweets.service.comment.CommentServiceImpl;
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

public class CommentControllerTest {

    private CommentController commentController;
    private CommentServiceImpl commentService;
    private UserHelper userHelper;
    private AuthenticationHelper authenticationHelper;

    private AuthenticationManager authenticationManager;

    private User user;

    @BeforeEach
    void setUp() {
        commentService = Mockito.mock(CommentServiceImpl.class);
        userHelper = Mockito.mock(UserHelper.class);
        authenticationHelper = Mockito.mock(AuthenticationHelper.class);
        commentController = new CommentController(commentService, userHelper, authenticationHelper);
        authenticationManager = Mockito.mock(AuthenticationManager.class);
        user = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
    }

    @Test
    public void addComment() throws AuthorizationException, TweetNotFoundException {
        CommentRequest commentRequest = new CommentRequest("test Comment");
        TweetRequest tweetRequest = new TweetRequest("Hello World!");
        Tweet tweet = new Tweet(1L, tweetRequest.getContent(), user, new Date(), null);
        when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        when(authenticationHelper.authorizeRequest("token")).thenReturn(true);
        when(commentService.addComment(commentRequest, tweet.getId(), "token")).thenReturn("added");
        assertThat(commentController.addComment(user.getUsername(), tweet.getId(), commentRequest, "token")).isNotNull();
    }

    @Test
    public void addCommentShouldThrowException() {
        CommentRequest commentRequest = new CommentRequest("test Comment");
        TweetRequest tweetRequest = new TweetRequest("Hello World!");
        Tweet tweet = new Tweet(1L, tweetRequest.getContent(), user, new Date(), null);
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        when(authenticationHelper.authorizeRequest("token1")).thenReturn(false);
        assertThatThrownBy(() -> commentController.addComment(user.getUsername(), tweet.getId(), commentRequest, "token"))
                .isInstanceOf(AuthorizationException.class).hasMessage("UNAUTHORIZED_REQUEST");
    }

    @Test
    public void deleteComment() throws AuthorizationException, CommentNotFoundException {
        TweetRequest tweetRequest = new TweetRequest("Hello World!");
        Tweet tweet = new Tweet(1L, tweetRequest.getContent(), user, new Date(), null);
        CommentRequest commentRequest = new CommentRequest("test Comment");
        Comment comment = new Comment(1L, commentRequest.getContent(), null, null, user, tweet);

        when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        when(authenticationHelper.authorizeRequest("token")).thenReturn(true);
        when(commentService.deleteComment(comment.getId(), "token")).thenReturn("deleted");
        assertThat(commentController.deleteComment(user.getUsername(), tweet.getId(), "token"));
    }

    @Test
    public void deleteCommentShouldThrowAnException() {
        CommentRequest commentRequest = new CommentRequest("test Comment");
        TweetRequest tweetRequest = new TweetRequest("Hello World!");
        Tweet tweet = new Tweet(1L, tweetRequest.getContent(), user, new Date(), null);
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        when(authenticationHelper.authorizeRequest("token1")).thenReturn(false);
        assertThatThrownBy(() -> commentController.deleteComment(user.getUsername(), tweet.getId(), "token"))
                .isInstanceOf(AuthorizationException.class).hasMessage("UNAUTHORIZED_REQUEST");
    }
}
