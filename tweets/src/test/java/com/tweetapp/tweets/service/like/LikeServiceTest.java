package com.tweetapp.tweets.service.like;

import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.like.Like;
import com.tweetapp.tweets.model.tweet.Tweet;
import com.tweetapp.tweets.model.tweet.TweetRequest;
import com.tweetapp.tweets.repository.LikeRepository;
import com.tweetapp.tweets.repository.TweetRepository;
import com.tweetapp.tweets.repository.UserRepository;
import com.tweetapp.tweets.util.UserHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class LikeServiceTest {

    private LikeServiceImpl likeService;
    private UserHelper userHelper;
    private UserRepository userRepository;
    private TweetRepository tweetRepository;
    private LikeRepository likeRepository;

    private User user;

    @BeforeEach
    void setUp() throws Exception {
        userHelper = Mockito.mock(UserHelper.class);
        userRepository = Mockito.mock(UserRepository.class);
        tweetRepository = Mockito.mock(TweetRepository.class);
        likeRepository = Mockito.mock(LikeRepository.class);
        likeService = new LikeServiceImpl(userRepository, userHelper, tweetRepository, likeRepository);
        user = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
    }


    @Test
    void addLikeShouldThrowException() {
        TweetRequest tweetRequest = new TweetRequest("New Tweet");
        Mockito.when((userHelper.getUsernameFromRequestHeader("token"))).thenReturn("megha");
        Mockito.when(userRepository.findByUsername("megha")).thenReturn(user);
        User loginUser = userRepository.findByUsername(userHelper.getUsernameFromRequestHeader("token"));
        Tweet tweet = new Tweet(1L, "hello", loginUser, null, null);
        assertThatThrownBy(() -> likeService.addLike(tweet.getId(), "token"))
                .isInstanceOf(TweetNotFoundException.class)
                .hasMessage("Tweet with id 1 does not exists");
    }

    @Test
    void addLikeTest() throws TweetNotFoundException {
        User loginUser = userRepository.findByUsername(userHelper.getUsernameFromRequestHeader("token"));
        Tweet tweet = new Tweet(1L, "Hello World!", user, null, null);
        Like like = new Like();
        like.setId(1L);
        like.setUser(loginUser);
        like.setTweet(tweet);
        List<Like> likes = new ArrayList<>();
        likes.add(like);
        Mockito.when(tweetRepository.findById(1L)).thenReturn(Optional.of(tweet));

        assertThat(likeService.addLike(tweet.getId(), "token")).isNotNull();
        Mockito.when(likeRepository.save(like)).thenReturn(like);
    }

    @Test
    void deleteLikeShouldThrowException() {
        TweetRequest tweetRequest = new TweetRequest("New Tweet");
        Mockito.when((userHelper.getUsernameFromRequestHeader("token"))).thenReturn("megha");
        Mockito.when(userRepository.findByUsername("megha")).thenReturn(user);
        User loginUser = userRepository.findByUsername(userHelper.getUsernameFromRequestHeader("token"));
        Tweet tweet = new Tweet(1L, "hello", loginUser, null, null);
        assertThatThrownBy(() -> likeService.deleteLike(tweet.getId(), "token"))
                .isInstanceOf(TweetNotFoundException.class)
                .hasMessage("Tweet with id 1 does not exists");
    }

    @Test
    void deleteLike() throws TweetNotFoundException {
        Mockito.when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        Mockito.when(userRepository.findByUsername("hightarun")).thenReturn(user);
        User loginUser = userRepository.findByUsername(userHelper.getUsernameFromRequestHeader("token"));
        Tweet tweet1 = new Tweet(1L, "Hey world!", user, null, null);
        Mockito.when(tweetRepository.findById(tweet1.getId())).thenReturn(Optional.of(tweet1));
        Like like = new Like();
        like.setId(1L);
        like.setUser(loginUser);
        like.setTweet(tweet1);
        List<Like> likes = new ArrayList<>();
        likes.add(like);
        Mockito.when(likeRepository.findLikeByTweetId(tweet1.getId())).thenReturn(likes);
        assertThat(likeService.deleteLike(tweet1.getId(), "token")).isNotNull();

    }

}
