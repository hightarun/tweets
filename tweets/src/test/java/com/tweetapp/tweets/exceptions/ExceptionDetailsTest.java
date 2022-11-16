package com.tweetapp.tweets.exceptions;

import com.tweetapp.tweets.exception.ExceptionDetails;
import com.tweetapp.tweets.model.comment.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ExceptionDetailsTest {

    private Map<String, String> errorMap = new HashMap<>();
    private LocalDateTime timeStamp;
    private ExceptionDetails exceptionDetails;

    @BeforeEach
    void setUp() {
        errorMap.put("key", "value");
        timeStamp = LocalDateTime.now();
        exceptionDetails = new ExceptionDetails(timeStamp, errorMap);
    }

    @Test
    void setterTest() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        LocalDateTime date = LocalDateTime.now();
        this.errorMap = map;
        this.timeStamp = date;
        exceptionDetails = new ExceptionDetails(timeStamp, errorMap);
        assertThat(exceptionDetails.getErrorMap().equals(map));
        assertThat(exceptionDetails.getTimeStamp().equals(date));

    }

    @Test
    void testTimeStampSetter() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");
        LocalDateTime date = LocalDateTime.now();
        exceptionDetails.setTimeStamp(date);
        assertThat(exceptionDetails.getTimeStamp().equals(date));
        assertThat(exceptionDetails.getErrorMap().equals(map));
    }

}
