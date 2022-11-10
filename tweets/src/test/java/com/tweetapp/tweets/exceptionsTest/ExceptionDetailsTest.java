package com.tweetapp.tweets.exceptionsTest;

import com.tweetapp.tweets.exception.ExceptionDetails;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ExceptionDetailsTest {

    private Map<String,String> errorMap;
    private ExceptionDetails exceptionDetails = new ExceptionDetails(LocalDateTime.now() , errorMap);

    @Test
    void testTimeStampSetter() {
        LocalDateTime date = LocalDateTime.now();
        exceptionDetails.setTimeStamp(date);
        assertThat(exceptionDetails.getTimeStamp().equals(date));

    }

}
