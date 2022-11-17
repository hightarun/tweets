package com.tweetapp.tweets.service.comment;

import com.tweetapp.tweets.exception.comment.CommentNotFoundException;
import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.comment.Comment;
import com.tweetapp.tweets.model.comment.CommentRequest;
import com.tweetapp.tweets.model.tweet.Tweet;
import com.tweetapp.tweets.repository.CommentRepository;
import com.tweetapp.tweets.repository.TweetRepository;
import com.tweetapp.tweets.repository.UserRepository;
import com.tweetapp.tweets.util.UserHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class CommentServiceTest {

    private CommentServiceImpl commentService;
    private UserRepository userRepository;
    private TweetRepository tweetRepository;
    private CommentRepository commentRepository;
    private UserHelper userHelper;

    private Tweet tweet;
    private User user;

    @BeforeEach
    void setUp() throws Exception {
        userRepository = Mockito.mock(UserRepository.class);
        tweetRepository = Mockito.mock(TweetRepository.class);
        commentRepository = Mockito.mock(CommentRepository.class);
        userHelper = Mockito.mock(UserHelper.class);
        commentService = new CommentServiceImpl(userRepository, tweetRepository, commentRepository, userHelper);
        user = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
        tweet = new Tweet(1L, "Hello World", user, null, null);
    }

    @Test
    void addComment() throws TweetNotFoundException {
        CommentRequest commentRequest = new CommentRequest("Amazing!");
        User loginUser = userRepository.findByUsername(userHelper.getUsernameFromRequestHeader("token"));
        Comment comment = new Comment(1L, commentRequest.getContent(), null, null, loginUser, tweet);
        Mockito.when(tweetRepository.findById(1L)).thenReturn(Optional.of(tweet));
        assertThat(commentService.addComment(commentRequest, tweet.getId(), "token")).isNotNull();
        Mockito.when(commentRepository.save(comment)).thenReturn(comment);
    }

    @Test
    public void addCommentShouldThrowException() {
        CommentRequest commentRequest = new CommentRequest("Amazing!");
        User loginUser = userRepository.findByUsername(userHelper.getUsernameFromRequestHeader("token"));
        Comment comment = new Comment(1L, commentRequest.getContent(), null, null, loginUser, tweet);
        Mockito.when(tweetRepository.findById(1L)).thenReturn(Optional.of(tweet));
        assertThatThrownBy(() -> commentService.addComment(commentRequest, 2L, "token"))
                .isInstanceOf(TweetNotFoundException.class).hasMessage("Tweet with id 2 does not exists");
    }

    @Test
    void deleteComment() throws CommentNotFoundException {
        CommentRequest commentRequest = new CommentRequest("Hi Everyone!");
        Mockito.when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        Mockito.when(userRepository.findByUsername("hightarun")).thenReturn(user);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Comment comment = new Comment(1L, commentRequest.getContent(), null, null, user, tweet);

        Mockito.when(commentRepository.findById(comment.getId())).thenReturn(Optional.of(comment));
        assertThat(commentService.deleteComment(1L, "token")).isNotNull();
    }

    @Test
    public void deleteCommentShouldThrowAnException() {
        CommentRequest commentRequest = new CommentRequest("Hi Everyone!");
        Mockito.when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        Mockito.when(userRepository.findByUsername("hightarun")).thenReturn(user);
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User loginUser = new User(2L, "Megha", "Samant", "samant@gmail.com", "megha", "123456789", "96543135540", null);
        Comment comment = new Comment(1L, commentRequest.getContent(), null, null, loginUser, tweet);
        //assertNotEquals()
        assertThatThrownBy(() -> commentService.deleteComment(comment.getId(), "token"))
                .isInstanceOf(CommentNotFoundException.class).hasMessage("Comment not found with id 1");
    }
}
