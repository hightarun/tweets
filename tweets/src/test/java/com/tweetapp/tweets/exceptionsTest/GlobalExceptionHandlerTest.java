package com.tweetapp.tweets.exceptionsTest;

import com.tweetapp.tweets.exception.ExceptionDetails;
import com.tweetapp.tweets.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Map;

@SpringBootTest
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private ExceptionDetails exceptionDetails;

    private Map<String,String> errorMap;

    @BeforeEach
    void setUp() {
        exceptionDetails = new ExceptionDetails(LocalDateTime.now(), errorMap);
    }


}
