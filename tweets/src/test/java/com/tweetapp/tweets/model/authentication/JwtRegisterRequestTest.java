package com.tweetapp.tweets.model.authentication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtRegisterRequestTest {
    private JwtRegisterRequest jwtRegisterRequest;

    @Test
    void test(){
        jwtRegisterRequest = new JwtRegisterRequest("Tarun" , "Bisht" , "tarun@gmail.com" , "hightarun" ,"12345678" ,"8929409364" );
        Assertions.assertEquals(jwtRegisterRequest.getFirstName() , "Tarun");
        Assertions.assertEquals(jwtRegisterRequest.getLastName() , "Bisht");
        Assertions.assertEquals(jwtRegisterRequest.getUsername() , "hightarun");
        Assertions.assertEquals(jwtRegisterRequest.getPassword() , "12345678");
        Assertions.assertEquals(jwtRegisterRequest.getContactNumber() , "8929409364");
    }

    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(JwtRegisterRequest.class);
    }
}
