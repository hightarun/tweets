package com.tweetapp.tweets.serviceTest.tweet;

import com.tweetapp.tweets.model.tweet.Tweet;
import com.tweetapp.tweets.repository.TweetRepository;
import com.tweetapp.tweets.repository.UserRepository;
import com.tweetapp.tweets.service.tweet.TweetService;
import com.tweetapp.tweets.service.tweet.TweetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TweetServiceTest {

    @MockBean
    private TweetServiceImpl tweetService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TweetRepository tweetRepository;

    @BeforeEach
    void setUp() throws Exception {
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
