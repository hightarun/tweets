package com.tweetapp.tweets.service.like;

import com.tweetapp.tweets.exception.like.AlreadyLikedException;
import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.like.Like;
import com.tweetapp.tweets.model.like.LikeResponse;
import com.tweetapp.tweets.model.tweet.Tweet;
import com.tweetapp.tweets.model.tweet.TweetResponse;
import com.tweetapp.tweets.repository.LikeRepository;
import com.tweetapp.tweets.repository.TweetRepository;
import com.tweetapp.tweets.repository.UserRepository;
import com.tweetapp.tweets.util.UserHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LikeServiceImpl implements LikeService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Override
    public String addLike(Long tweetId, String token) throws TweetNotFoundException, AlreadyLikedException {
        User loggedInUser = userRepository.findByUsername(userHelper.getUsernameFromRequestHeader(token));
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new TweetNotFoundException("Tweet with id " + tweetId + " does not exists"));
        List<Like> likeTweetId = likeRepository.findLikeByTweetId(tweetId);
        for (Like l : likeTweetId) {
            if (l.getUser().getId() == loggedInUser.getId()) {
                throw new AlreadyLikedException("Cannot like twice");
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
            if (l.getUser().getId() == loggedInUser.getId()) {
                likeRepository.delete(l);
                log.info("Unliked");
                return ("Unliked");
            }
        }
        return "Already Unliked";
    }
}
