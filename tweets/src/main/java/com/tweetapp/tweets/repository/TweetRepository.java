package com.tweetapp.tweets.repository;

import com.tweetapp.tweets.model.tweet.Tweet;
import com.tweetapp.tweets.model.tweet.TweetResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {

    @Query(value = "select * from tweets where user_id = :uid order by update_time desc", nativeQuery = true)
    List<Tweet> findTweetsByUser(@Param("uid") Long id);
}
