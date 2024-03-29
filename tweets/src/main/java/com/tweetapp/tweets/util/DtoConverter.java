package com.tweetapp.tweets.util;

import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.authentication.UserDetailsResponse;
import com.tweetapp.tweets.model.comment.Comment;
import com.tweetapp.tweets.model.comment.CommentResponse;
import com.tweetapp.tweets.model.like.Like;
import com.tweetapp.tweets.model.like.LikeResponse;
import com.tweetapp.tweets.model.tweet.Tweet;
import com.tweetapp.tweets.model.tweet.TweetResponse;
import com.tweetapp.tweets.model.tweet.TweetUser;
import com.tweetapp.tweets.repository.CommentRepository;
import com.tweetapp.tweets.repository.LikeRepository;
import com.tweetapp.tweets.repository.TweetRepository;
import com.tweetapp.tweets.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DtoConverter {

    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Autowired
    public DtoConverter(UserRepository userRepository, TweetRepository tweetRepository, CommentRepository commentRepository, LikeRepository likeRepository) {
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
    }


    //Convert to tweet response DTO
    public TweetResponse convertToTweetResponse(Tweet tweet) {
        TweetResponse tweetResponse = new TweetResponse();
        TweetUser tweetUser = new TweetUser();
        List<CommentResponse> comments = commentRepository.findCommentByTweetId(tweet.getId()).stream().map(this::convertToCommentResponse).collect(Collectors.toList());
        List<LikeResponse> likes = likeRepository.findLikeByTweetId(tweet.getId()).stream().map(this::convertToLikeResponse).collect(Collectors.toList());
        tweetResponse.setComments(comments);
        tweetResponse.setLikes(likes);
        tweetResponse.setContent(tweet.getContent());
        tweetResponse.setId(tweet.getId());
        tweetResponse.setCreateTime(tweet.getCreateTime());
        tweetResponse.setUpdateTime(tweet.getUpdateTime());
        tweetUser.setUser_id(tweet.getUser().getId());
        tweetUser.setFirstName(tweet.getUser().getFirstName());
        tweetUser.setLastName(tweet.getUser().getLastName());
        tweetUser.setUsername(tweet.getUser().getUsername());
        tweetResponse.setTweetUser(tweetUser);
        return tweetResponse;
    }

    public UserDetailsResponse convertToUserDetailsResponse(User user) {
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setId(user.getId());
        userDetailsResponse.setUsername(user.getUsername());
        userDetailsResponse.setFirstName(user.getFirstName());
        userDetailsResponse.setLastName(user.getLastName());
        userDetailsResponse.setEmail(user.getEmail());
        return userDetailsResponse;
    }

    public CommentResponse convertToCommentResponse(Comment comment) {
        UserDetailsResponse userDetailsResponse = convertToUserDetailsResponse(comment.getUser());

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setId(comment.getId());
        commentResponse.setCommentUser(userDetailsResponse);
        commentResponse.setCommentTweetId(comment.getTweet().getId());
        commentResponse.setContent(comment.getContent());
        commentResponse.setCreateTime(comment.getCreateTime());
        return commentResponse;
    }

    public LikeResponse convertToLikeResponse(Like like) {
        LikeResponse likeResponse = new LikeResponse();
        likeResponse.setId(like.getId());
        likeResponse.setLikeUserId(like.getUser().getId());
        likeResponse.setLikeTweetId(like.getTweet().getId());
        return likeResponse;
    }
}
