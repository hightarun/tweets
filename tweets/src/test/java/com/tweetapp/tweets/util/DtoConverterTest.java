package com.tweetapp.tweets.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.comment.Comment;
import com.tweetapp.tweets.model.like.Like;
import com.tweetapp.tweets.model.tweet.Tweet;
import org.junit.jupiter.api.Assertions;
import com.tweetapp.tweets.model.authentication.UserDetailsResponse;
import com.tweetapp.tweets.model.comment.CommentResponse;
import com.tweetapp.tweets.model.like.LikeResponse;
import com.tweetapp.tweets.model.tweet.TweetResponse;
import com.tweetapp.tweets.model.tweet.TweetUser;
import com.tweetapp.tweets.repository.CommentRepository;
import com.tweetapp.tweets.repository.LikeRepository;
import com.tweetapp.tweets.repository.TweetRepository;
import com.tweetapp.tweets.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class DtoConverterTest {

    private UserRepository userRepository;

    private TweetRepository tweetRepository;

    private CommentRepository commentRepository;

    private LikeRepository likeRepository;

    private TweetUser tweetUser;

    private UserDetailsResponse userDetailsResponse;

    private CommentResponse commentResponse;

    private LikeResponse likeResponse;

    private DtoConverter dtoConverter;

    private User user;


    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        tweetRepository = Mockito.mock(TweetRepository.class);
        commentRepository = Mockito.mock(CommentRepository.class);
        likeRepository = Mockito.mock(LikeRepository.class);
        tweetUser = new TweetUser(1L, "Tarun", "Bisht", "hightarun");
        userDetailsResponse = new UserDetailsResponse(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun");
        commentResponse = new CommentResponse(1L, "Hello World", userDetailsResponse, 1L, new Date());
        likeResponse = new LikeResponse(1L, 1L, tweetUser.getUser_id());
        user = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
        dtoConverter = new DtoConverter(userRepository, tweetRepository, commentRepository, likeRepository);

    }

    @Test
    void convertToTweetResponseTest() {
        List<CommentResponse> commentResponses = new ArrayList<>();
        commentResponses.add(commentResponse);
        List<LikeResponse> likeResponses = new ArrayList<>();
        likeResponses.add(likeResponse);
        Tweet tweet = new Tweet(1L, "Hello", user, null, null);
        TweetResponse tweetResponse = new TweetResponse(1L, "Hello World!", tweetUser, commentResponses, likeResponses, null, null);
        assertThat(dtoConverter.convertToTweetResponse(tweet)).isNotNull();
    }

    @Test
    void convertToUserDetailsResponseTest() {
        assertThat(dtoConverter.convertToUserDetailsResponse(user)).isNotNull();
    }

    @Test
    void convertToCommentResponseTest() {
        Tweet tweet = new Tweet(1L, "Hello", user, null, null);
        Comment comment = new Comment(1L, "Hello", null, null, user, tweet);
        assertThat(dtoConverter.convertToCommentResponse(comment)).isNotNull();
    }

    @Test
    void convertToLikeResponseTest() {
        Tweet tweet = new Tweet(1L, "Hello", user, null, null);
        Like like = new Like(1L, null, user, tweet);
        assertThat(dtoConverter.convertToLikeResponse(like)).isNotNull();
    }
}
