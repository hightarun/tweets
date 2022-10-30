package com.tweetapp.tweets.service.tweet;

import com.tweetapp.tweets.exception.authentication.UsernameNotExistsException;
import com.tweetapp.tweets.exception.tweet.TweetNotAuthorizedException;
import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.comment.Comment;
import com.tweetapp.tweets.model.like.Like;
import com.tweetapp.tweets.model.tweet.Tweet;
import com.tweetapp.tweets.model.tweet.TweetRequest;
import com.tweetapp.tweets.model.tweet.TweetResponse;
import com.tweetapp.tweets.repository.CommentRepository;
import com.tweetapp.tweets.repository.LikeRepository;
import com.tweetapp.tweets.repository.TweetRepository;
import com.tweetapp.tweets.repository.UserRepository;
import com.tweetapp.tweets.util.DtoConverter;
import com.tweetapp.tweets.util.UserHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TweetServiceImpl implements TweetService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private DtoConverter dtoConverter;

    @Override
    public String addTweets(TweetRequest tweetRequest, String token) {
        User loggedInUser = userRepository.findByUsername(userHelper.getUsernameFromRequestHeader(token));
        Tweet newTweet = new Tweet();
        newTweet.setUser(loggedInUser);
        newTweet.setContent(tweetRequest.getContent());
        tweetRepository.save(newTweet);
        log.info("Tweet added");
        return "Tweeted successfully";
    }

    @Override
    public String updateTweets(TweetRequest tweetRequest, String token, Long id) throws UsernameNotExistsException, TweetNotFoundException, TweetNotAuthorizedException {
        String userFromToken = userHelper.getUsernameFromRequestHeader(token);
        User loggedInUser = userRepository.findByUsername(userFromToken);
        if (loggedInUser == null) {
            throw new UsernameNotExistsException("User with username " + userFromToken + " does not exists");
        }
        Tweet updateTweet = tweetRepository.findById(id).orElseThrow(() -> new TweetNotFoundException("Tweet does not exists with id " + id));
        if (updateTweet.getUser() != loggedInUser) {
            throw new TweetNotAuthorizedException("Not Authorized to change the tweet.");
        }
        updateTweet.setContent(tweetRequest.getContent());
        tweetRepository.save(updateTweet);
        log.info("Tweet updated");
        return "Tweet updated successfully";
    }

    @Override
    public String deleteTweet(String token, Long id) throws UsernameNotExistsException, TweetNotFoundException, TweetNotAuthorizedException {
        String userFromToken = userHelper.getUsernameFromRequestHeader(token);
        User loggedInUser = userRepository.findByUsername(userFromToken);
        if (loggedInUser == null) {
            throw new UsernameNotExistsException("User with username " + userFromToken + " does not exists");
        }
        Tweet deleteTweet = tweetRepository.findById(id).orElseThrow(() -> new TweetNotFoundException("Tweet does not exists with id " + id));
        if (deleteTweet.getUser() != loggedInUser) {
            throw new TweetNotAuthorizedException("Not Authorized to change the tweet.");
        }

        List<Comment> comments = commentRepository.findCommentByTweetId(id);
        List<Like> likes = likeRepository.findLikeByTweetId(id);
        commentRepository.deleteAll(comments);
        likeRepository.deleteAll(likes);
        log.info("comment and likes deleted");

        tweetRepository.delete(deleteTweet);
        log.info("Tweet deleted");

        return "Tweet deleted successfully";

    }

    @Override
    public List<TweetResponse> getAllTweetsUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " does not exists");
        }
        return tweetRepository.findTweetsByUser(user.getId()).stream().map(tweet -> dtoConverter.convertToTweetResponse(tweet)).collect(Collectors.toList());
    }

    @Override
    public List<TweetResponse> getAllTweet() {
        return tweetRepository.findAll().stream().map(tweet -> dtoConverter.convertToTweetResponse(tweet)).collect(Collectors.toList());
    }

}