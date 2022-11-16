package com.tweetapp.tweets.service.comment;

import com.tweetapp.tweets.exception.comment.CommentActionNotAuthorized;
import com.tweetapp.tweets.exception.comment.CommentNotFoundException;
import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import com.tweetapp.tweets.model.authentication.User;
import com.tweetapp.tweets.model.comment.Comment;
import com.tweetapp.tweets.model.comment.CommentRequest;
import com.tweetapp.tweets.model.tweet.Tweet;
import com.tweetapp.tweets.repository.CommentRepository;
import com.tweetapp.tweets.repository.TweetRepository;
import com.tweetapp.tweets.repository.UserRepository;
import com.tweetapp.tweets.util.UserHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final TweetRepository tweetRepository;
    private final CommentRepository commentRepository;
    private final UserHelper userHelper;

    @Autowired
    public CommentServiceImpl(UserRepository userRepository, TweetRepository tweetRepository, CommentRepository commentRepository, UserHelper userHelper) {
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.commentRepository = commentRepository;
        this.userHelper = userHelper;
    }

    @Override
    public String addComment(CommentRequest commentRequest, Long tweetId, String token) throws TweetNotFoundException {
        User loggedInUser = userRepository.findByUsername(userHelper.getUsernameFromRequestHeader(token));
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new TweetNotFoundException("Tweet with id " + tweetId + " does not exists"));
        Comment newComment = new Comment();
        newComment.setUser(loggedInUser);
        newComment.setTweet(tweet);
        newComment.setContent(commentRequest.getContent());
        commentRepository.save(newComment);
        log.info("Commented successfully");
        return "Successfully commented";
    }

    @Override
    public String deleteComment(Long commentId, String token) throws CommentNotFoundException, CommentActionNotAuthorized {
        User loggedInUser = userRepository.findByUsername(userHelper.getUsernameFromRequestHeader(token));
        Comment commentedTweet = commentRepository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Comment not found with id " + commentId));
        User commentUser = userRepository.findById(commentedTweet.getUser().getId()).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        if (commentUser.getId() != (loggedInUser.getId())) {
            throw new CommentActionNotAuthorized("Unauthorized to delete the comment");
        }
        commentRepository.delete(commentedTweet);
        log.info("Comment deleted");
        return "Comment deleted";
    }
}
