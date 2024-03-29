package com.tweetapp.tweets.service.like;


import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.like.Like;
import com.tweetapp.tweets.model.tweet.Tweet;
import com.tweetapp.tweets.repository.LikeRepository;
import com.tweetapp.tweets.repository.TweetRepository;
import com.tweetapp.tweets.repository.UserRepository;
import com.tweetapp.tweets.util.UserHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LikeServiceImpl implements LikeService {

    private final UserHelper userHelper;
    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final LikeRepository likeRepository;

    public LikeServiceImpl(UserRepository userRepository, UserHelper userHelper, TweetRepository tweetRepository, LikeRepository likeRepository) {
        this.userHelper = userHelper;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.likeRepository = likeRepository;
    }

    @Override
    public String addLike(Long tweetId, String token) throws TweetNotFoundException {
        User loggedInUser = userRepository.findByUsername(userHelper.getUsernameFromRequestHeader(token));
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new TweetNotFoundException("Tweet with id " + tweetId + " does not exists"));
        List<Like> likeTweetId = likeRepository.findLikeByTweetId(tweetId);
        for (Like l : likeTweetId) {
            if (l.getUser().getId().equals(loggedInUser.getId())) {
                return "Cannot like twice";
            }
        }
        Like newLike = new Like();
        newLike.setUser(loggedInUser);
        newLike.setTweet(tweet);
        likeRepository.save(newLike);
        log.info("Liked the tweet");
        return "Liked";
    }

    @Override
    public String deleteLike(Long tweetId, String token) throws TweetNotFoundException {
        User loggedInUser = userRepository.findByUsername(userHelper.getUsernameFromRequestHeader(token));
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new TweetNotFoundException("Tweet with id " + tweetId + " does not exists"));
        List<Like> likedTweet = likeRepository.findLikeByTweetId(tweetId);
        for (Like l : likedTweet) {
            if (l.getUser().getId().equals(loggedInUser.getId())) {
                likeRepository.delete(l);
                log.info("Unliked");
                return ("Unliked");
            }
        }
        return "Already Unliked";
    }
}
