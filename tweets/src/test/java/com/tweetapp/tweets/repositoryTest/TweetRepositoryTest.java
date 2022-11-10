package com.tweetapp.tweets.repositoryTest;

import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.tweet.Tweet;
import com.tweetapp.tweets.repository.TweetRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TweetRepositoryTest {

    @Mock
    private TweetRepository tweetRepository;

    @Test
    void findTweetByUser() {
        User u = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "123456789", "8929409364", null);
        Tweet tweet = new Tweet();
        tweet.setId(1L);
        tweet.setUser(u);
        tweet.setContent("Welcome !");
        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet);
        Mockito.when(tweetRepository.findTweetsByUser(1L)).thenReturn(tweets);
    }
}
