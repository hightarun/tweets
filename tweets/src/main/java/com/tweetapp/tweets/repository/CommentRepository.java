package com.tweetapp.tweets.repository;

import com.tweetapp.tweets.model.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select * from comments where tweet_id = :tid order by update_time desc", nativeQuery = true)
    List<Comment> findCommentByTweetId(@Param("tid") Long id);
}
