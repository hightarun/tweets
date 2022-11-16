package com.tweetapp.tweets.model.authentication;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtResponseTest {
    private JwtResponse jwtResponse;

    @BeforeEach
    void setUp() throws Exception{
        jwtResponse = new JwtResponse("token");
    }

    @Test
    void test(){
        assertThat(jwtResponse.getToken().equals("token"));
    }

}
