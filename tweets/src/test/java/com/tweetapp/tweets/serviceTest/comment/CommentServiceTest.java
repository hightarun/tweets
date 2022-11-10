package com.tweetapp.tweets.serviceTest.comment;

import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.comment.Comment;
import com.tweetapp.tweets.repository.CommentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class CommentServiceTest {


    @Mock
    private CommentRepository commentRepository;

    @BeforeEach
    void setUp() throws Exception {
    }


    @Test
    void addComment(){
       Comment comment = new Comment();
       comment.setId(1L);
       comment.setComment("first Comment");
       Mockito.when(commentRepository.save(comment)).thenReturn(comment);
       assertThat(commentRepository).isNotNull();
    }

    @Test
    void deleteComment(){
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setComment("first Comment");
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        Mockito.when(commentRepository.findCommentByTweetId(1L)).thenReturn(comments);
        commentRepository.delete(comment);
    }
}
