package com.tweetapp.tweets.repositoryTest;

import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.like.Like;
import com.tweetapp.tweets.repository.LikeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LikeRepositoryTest {

    @Mock
    private LikeRepository likeRepository;

    @Test
    void findLikeByTweetId() {
        User u = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "123456789", "8929409364", null);
        Like like = new Like();
        like.setId(1L);
        like.setUser(u);
        List<Like> likes = new ArrayList<>();
        likes.add(like);
        Mockito.when(likeRepository.findLikeByTweetId(1L)).thenReturn(likes);
        assertThat(likeRepository.findLikeByTweetId(1L)).isNotNull();
    }
}
