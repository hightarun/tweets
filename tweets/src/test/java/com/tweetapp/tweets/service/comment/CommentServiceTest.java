package com.tweetapp.tweets.service.comment;

import com.tweetapp.tweets.model.comment.Comment;
import com.tweetapp.tweets.repository.CommentRepository;
import com.tweetapp.tweets.repository.TweetRepository;
import com.tweetapp.tweets.repository.UserRepository;
import com.tweetapp.tweets.util.UserHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class CommentServiceTest {

    private CommentServiceImpl commentService;
    private UserRepository userRepository;
    private TweetRepository tweetRepository;
    private CommentRepository commentRepository;
    private UserHelper userHelper;

    @BeforeEach
    void setUp() throws Exception {
        userRepository = Mockito.mock(UserRepository.class);
        tweetRepository = Mockito.mock(TweetRepository.class);
        commentRepository = Mockito.mock(CommentRepository.class);
        userHelper = Mockito.mock(UserHelper.class);
        commentService = new CommentServiceImpl(userRepository, tweetRepository, commentRepository, userHelper);
    }

    @Test
    void addComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setComment("first Comment");
        Mockito.when(commentRepository.save(comment)).thenReturn(comment);
        assertThat(commentRepository).isNotNull();
    }

    @Test
    void deleteComment() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setComment("first Comment");
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        Mockito.when(commentRepository.findCommentByTweetId(1L)).thenReturn(comments);
        commentRepository.delete(comment);
    }
}
