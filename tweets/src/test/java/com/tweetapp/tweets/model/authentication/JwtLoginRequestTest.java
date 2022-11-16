package com.tweetapp.tweets.model.authentication;


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtLoginRequestTest {
    private JwtLoginRequest jwtLoginRequest;

    @BeforeEach
    void setUp() {
        jwtLoginRequest = new JwtLoginRequest("hightarun", "12345678");
    }

    @Test
    void testUserNameGetter() {
        assertThat(jwtLoginRequest.getUsername().equals("hightarun")).isTrue();
    }

    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(JwtLoginRequest.class);

    }
}