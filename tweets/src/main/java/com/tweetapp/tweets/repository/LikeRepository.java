package com.tweetapp.tweets.repository;

import com.tweetapp.tweets.model.like.Like;
import com.tweetapp.tweets.model.like.LikeResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query(value = "select * from likes where tweet_id = :tid", nativeQuery = true)
    List<Like> findLikeByTweetId(@Param("tid") Long id);
}
