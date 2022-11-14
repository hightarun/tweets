package com.tweetapp.tweets.service.comment;

import antlr.Token;
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


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.LOCAL_DATE;

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
        tweet = new Tweet(1l,"Hello World" , user ,null ,null );
    }

    @Test
    void addComment() throws TweetNotFoundException {
        CommentRequest commentRequest = new CommentRequest("First Comment");
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setComment(commentRequest.getComment());
        comment.setTweet(tweet);
        comment.setUser(user);
        Mockito.when(commentRepository.save(comment)).thenReturn(comment);
       // assertThat(commentService.addComment(commentRequest,tweet.getId(),"token")).isNotNull();
    }

    @Test
    void deleteComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setComment("first Comment");
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        Mockito.when(commentRepository.findCommentByTweetId(1L)).thenReturn(comments);
        commentRepository.delete(comment);
    }
}
