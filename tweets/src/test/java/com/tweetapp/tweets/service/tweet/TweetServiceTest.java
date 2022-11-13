package com.tweetapp.tweets.service.tweet;

import com.tweetapp.tweets.model.tweet.Tweet;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TweetServiceTest {

    private TweetServiceImpl tweetService;
    private UserRepository userRepository;
    private TweetRepository tweetRepository;
    private CommentRepository commentRepository;
    private LikeRepository likeRepository;
    private UserHelper userHelper;
    private DtoConverter dtoConverter;


    @BeforeEach
    void setUp() throws Exception {
        userRepository = Mockito.mock(UserRepository.class);
        tweetRepository = Mockito.mock(TweetRepository.class);
        commentRepository = Mockito.mock(CommentRepository.class);
        likeRepository = Mockito.mock(LikeRepository.class);
        userHelper = Mockito.mock(UserHelper.class);
        dtoConverter = Mockito.mock(DtoConverter.class);
        tweetService = new TweetServiceImpl(userRepository, tweetRepository, commentRepository, likeRepository, userHelper, dtoConverter);
    }


    @Test
    void addTweetsTest() {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setContent("Hello World");
        assertThat(tweetRepository).isNotNull();
        Mockito.when(tweetRepository.save(tweet)).thenReturn(tweet);
    }

    @Test
    void updatedTweetsTest() {
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setUser(userRepository.findByUsername("hightarun"));
        tweet.setContent("Hi");
        assertThat(tweetRepository).isNotNull();
        Mockito.when(tweetRepository.save(Mockito.any())).thenReturn(tweet);
    }

    @Test
    void deleteTweetTest() {

    }
}
