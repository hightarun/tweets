package com.tweetapp.tweets.serviceTest.like;

import com.tweetapp.tweets.model.like.Like;
import com.tweetapp.tweets.repository.LikeRepository;
import com.tweetapp.tweets.repository.UserRepository;
import com.tweetapp.tweets.util.UserHelper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class LikeServiceTest {

    @Mock
    private UserRepository userRepository;

    @MockBean
    private LikeRepository likeRepository;

    @Mock
    private UserHelper userHelper;

    @Test
    void addLikeTest(){
        Like like = new Like();
        like.setId(1L);
        assertThat(likeRepository).isNotNull();
        Mockito.when(likeRepository.save(like)).thenReturn(like);
    }
}
