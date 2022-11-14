package com.tweetapp.tweets.service.tweet;

import com.tweetapp.tweets.exception.authentication.UsernameNotExistsException;
import com.tweetapp.tweets.exception.tweet.TweetNotAuthorizedException;
import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.tweet.Tweet;
import com.tweetapp.tweets.model.tweet.TweetRequest;
import com.tweetapp.tweets.repository.CommentRepository;
import com.tweetapp.tweets.repository.LikeRepository;
import com.tweetapp.tweets.repository.TweetRepository;
import com.tweetapp.tweets.repository.UserRepository;
import com.tweetapp.tweets.util.DtoConverter;
import com.tweetapp.tweets.util.UserHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class TweetServiceTest {

    private TweetServiceImpl tweetService;
    private UserRepository userRepository;
    private TweetRepository tweetRepository;
    private CommentRepository commentRepository;
    private LikeRepository likeRepository;
    private UserHelper userHelper;
    private DtoConverter dtoConverter;

    private User user;

    @BeforeEach
    void setUp() throws Exception {
        userRepository = Mockito.mock(UserRepository.class);
        tweetRepository = Mockito.mock(TweetRepository.class);
        commentRepository = Mockito.mock(CommentRepository.class);
        likeRepository = Mockito.mock(LikeRepository.class);
        userHelper = Mockito.mock(UserHelper.class);
        dtoConverter = Mockito.mock(DtoConverter.class);
        tweetService = new TweetServiceImpl(userRepository, tweetRepository, commentRepository, likeRepository, userHelper, dtoConverter);
        user = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null);
    }


    @Test
    void addTweetsTest() {
        TweetRequest tweetRequest =new TweetRequest("New Tweet");
        Mockito.when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        User loginUser = userRepository.findByUsername(userHelper.getUsernameFromRequestHeader("token"));
        Tweet tweet = new Tweet(1L,tweetRequest.getContent(),user,new Date(),null);
        assertThat(tweetService.addTweets(tweetRequest,"token")).isNotNull();
        Mockito.when(tweetRepository.save(tweet)).thenReturn(tweet);
    }

    @Test
    void updatedTweetsShouldThrowExceptions(){
        TweetRequest tweetRequest =new TweetRequest("New Tweet");
        Mockito.when((userHelper.getUsernameFromRequestHeader("token"))).thenReturn("megha");
        Mockito.when(userRepository.findByUsername("megha")).thenReturn(user);
        User loginUser =userRepository.findByUsername(userHelper.getUsernameFromRequestHeader("token"));
        Tweet tweet = new Tweet(1L,"hello",loginUser,null,null);
        assertThatThrownBy(()->tweetService.updateTweets(tweetRequest,"token",1L))
                .isInstanceOf(TweetNotFoundException.class)
                .hasMessage("Tweet does not exists with id 1");
    }

    @Test
    void updatedTweetsTest() throws UsernameNotExistsException, TweetNotAuthorizedException, TweetNotFoundException {

        TweetRequest tweetRequest =new TweetRequest("New Tweet");
        Mockito.when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        Mockito.when(userRepository.findByUsername("hightarun")).thenReturn(user);
        //Mockito.when(tweetService.updateTweets(tweetRequest,"token",1L)).thenReturn("Tweet updated successfully");
        User loginUser = userRepository.findByUsername(userHelper.getUsernameFromRequestHeader("token"));
        Tweet tweet = new Tweet(1L,"Old tweet",loginUser,null,null);
        Mockito.when(tweetRepository.findById(1L)).thenReturn(Optional.of(tweet));
        assertThat(tweetService.updateTweets(tweetRequest,"token" , 1L)).isNotNull();
        Mockito.when(tweetRepository.save(tweet)).thenReturn(tweet);


    }

    @Test
    void deleteTweetsExceptionCheck(){
        Mockito.when(tweetRepository.findTweetsByUser(1L)).thenReturn(null);
        Mockito.when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("megha");
        assertThatThrownBy(()->tweetService.deleteTweet("token" , 1L))
                .isInstanceOf(UsernameNotExistsException.class).hasMessage("User with username megha does not exists");
    }

    @Test
    void deleteTweetTest() throws UsernameNotExistsException, TweetNotAuthorizedException, TweetNotFoundException {
        Mockito.when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        Mockito.when(userRepository.findByUsername("hightarun")).thenReturn(user);
        User loginUser = userRepository.findByUsername(userHelper.getUsernameFromRequestHeader("token"));

        Tweet tweet = new Tweet(1L,"Hello",loginUser,null,null);
        Mockito.when(tweetRepository.findById(1L)).thenReturn(Optional.of(tweet));

        assertThat(tweetService.deleteTweet("token" , 1L)).isNotNull();
    }

    @Test
    void getAllTweets(){
        Mockito.when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        Mockito.when(userRepository.findByUsername("hightarun")).thenReturn(user);
        User loginUser = userRepository.findByUsername(userHelper.getUsernameFromRequestHeader("token"));
        Tweet tweet = new Tweet(1L,"Hello",loginUser,null,null);

        List<Tweet> tweets= new ArrayList<>();
        tweets.add(tweet);
        Mockito.when(tweetRepository.findAll()).thenReturn(tweets);
        assertThat(tweetService.getAllTweet()).isNotNull();
    }


    @Test
    void getAllTweetsUserException(){
        Mockito.when(tweetRepository.findTweetsByUser(1L)).thenReturn(null);
        assertThatThrownBy(()->tweetService.getAllTweetsUser("megha"))
                .isInstanceOf(UsernameNotFoundException.class).hasMessage("User with username megha does not exists");
    }
    @Test
    void getAllTweetsUser(){
        Mockito.when(userHelper.getUsernameFromRequestHeader("token")).thenReturn("hightarun");
        Mockito.when(userRepository.findByUsername("hightarun")).thenReturn(user);
        User loginUser = userRepository.findByUsername(userHelper.getUsernameFromRequestHeader("token"));
        Tweet tweet = new Tweet(1L,"Hello",loginUser,null,null);
        List<Tweet> tweets= new ArrayList<>();
        tweets.add(tweet);
        Mockito.when(tweetRepository.findTweetsByUser(1L)).thenReturn(tweets);
        assertThat(tweetService.getAllTweetsUser("hightarun"));
    }

}
