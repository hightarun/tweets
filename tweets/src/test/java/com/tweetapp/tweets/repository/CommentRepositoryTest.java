package com.tweetapp.tweets.repository;

import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.comment.Comment;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class CommentRepositoryTest {

    @Mock
    private CommentRepository commentRepository;

    @Test
    void findCommentByTweetId() {
        User u = new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "123456789", "8929409364", null);
        Comment comment = new Comment();
        comment.setUser(u);
        comment.setComment("amazing!");
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        Mockito.when(commentRepository.findCommentByTweetId(1L)).thenReturn(comments);
        assertThat(commentRepository.findCommentByTweetId(1L)).isNotNull();
    }
}
