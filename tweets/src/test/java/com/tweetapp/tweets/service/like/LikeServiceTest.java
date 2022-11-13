package com.tweetapp.tweets.service.like;

import com.tweetapp.tweets.model.like.Like;
import com.tweetapp.tweets.repository.LikeRepository;
import com.tweetapp.tweets.repository.TweetRepository;
import com.tweetapp.tweets.repository.UserRepository;
import com.tweetapp.tweets.util.UserHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class LikeServiceTest {

    private LikeServiceImpl likeService;
    private UserHelper userHelper;
    private UserRepository userRepository;
    private TweetRepository tweetRepository;
    private LikeRepository likeRepository;

    @BeforeEach
    void setUp() throws Exception {
        userHelper = Mockito.mock(UserHelper.class);
        userRepository = Mockito.mock(UserRepository.class);
        tweetRepository = Mockito.mock(TweetRepository.class);
        likeRepository = Mockito.mock(LikeRepository.class);
        likeService = new LikeServiceImpl(userRepository, userHelper, tweetRepository, likeRepository);
    }

    @Test
    void addLikeTest() {
        Like like = new Like();
        like.setId(1L);
        assertThat(likeRepository).isNotNull();
        Mockito.when(likeRepository.save(like)).thenReturn(like);
    }
}
