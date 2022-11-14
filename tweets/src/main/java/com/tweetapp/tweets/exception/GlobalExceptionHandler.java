package com.tweetapp.tweets.exception;

import com.tweetapp.tweets.exception.authentication.AuthorizationException;
import com.tweetapp.tweets.exception.authentication.InvalidResetCodeException;
import com.tweetapp.tweets.exception.authentication.UsernameAlreadyExistsException;
import com.tweetapp.tweets.exception.authentication.UsernameNotExistsException;
import com.tweetapp.tweets.exception.comment.CommentActionNotAuthorized;
import com.tweetapp.tweets.exception.comment.CommentNotFoundException;
import com.tweetapp.tweets.exception.like.AlreadyLikedException;
import com.tweetapp.tweets.exception.tweet.TweetNotAuthorizedException;
import com.tweetapp.tweets.exception.tweet.TweetNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    static final String MESSAGE = "Message";

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(MESSAGE, ex.getMessage());
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), errorMap);
        log.error(ex.getMessage());
        return new ResponseEntity<>(exceptionDetails, status);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage()));
        ExceptionDetails exceptionDetail = new ExceptionDetails(LocalDateTime.now(), errorMap);
        return new ResponseEntity<>(exceptionDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(MESSAGE, ex.getMessage());
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), errorMap);
        log.error(ex.getMessage());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Object> handleGlobalException(AuthorizationException ex, WebRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(MESSAGE, ex.getMessage());
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), errorMap);
        log.error(ex.getMessage());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotExistsException.class)
    public ResponseEntity<Object> handleGlobalException(UsernameNotExistsException ex, WebRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(MESSAGE, ex.getMessage());
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), errorMap);
        log.error(ex.getMessage());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Object> handleGlobalException(UsernameAlreadyExistsException ex, WebRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(MESSAGE, ex.getMessage());
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), errorMap);
        log.error(ex.getMessage());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TweetNotFoundException.class)
    public ResponseEntity<Object> handleGlobalException(TweetNotFoundException ex, WebRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(MESSAGE, ex.getMessage());
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), errorMap);
        log.error(ex.getMessage());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TweetNotAuthorizedException.class)
    public ResponseEntity<Object> handleGlobalException(TweetNotAuthorizedException ex, WebRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(MESSAGE, ex.getMessage());
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), errorMap);
        log.error(ex.getMessage());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AlreadyLikedException.class)
    public ResponseEntity<Object> handleGlobalException(AlreadyLikedException ex, WebRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(MESSAGE, ex.getMessage());
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), errorMap);
        log.error(ex.getMessage());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<Object> handleGlobalException(CommentNotFoundException ex, WebRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(MESSAGE, ex.getMessage());
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), errorMap);
        log.error(ex.getMessage());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CommentActionNotAuthorized.class)
    public ResponseEntity<Object> handleGlobalException(CommentActionNotAuthorized ex, WebRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(MESSAGE, ex.getMessage());
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), errorMap);
        log.error(ex.getMessage());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidResetCodeException.class)
    public ResponseEntity<Object> handleGlobalException(InvalidResetCodeException ex, WebRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(MESSAGE, ex.getMessage());
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), errorMap);
        log.error(ex.getMessage());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TryCatchException.class)
    public ResponseEntity<Object> handleGlobalException(TryCatchException ex, WebRequest request) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put(MESSAGE, ex.getMessage());
        ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now(), errorMap);
        log.error(ex.getMessage());
        return new ResponseEntity<>(exceptionDetails, HttpStatus.UNAUTHORIZED);
    }

}
