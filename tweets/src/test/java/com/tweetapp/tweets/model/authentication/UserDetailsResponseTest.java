package com.tweetapp.tweets.model.authentication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserDetailsResponseTest {
    private UserDetailsResponse userDetailsResponse1;

    private UserDetailsResponse userDetailsResponse2;

    private  User user;
    @BeforeEach
    void setUp(){

    }

    @Test
    void test(){

        UserDetailsResponse userDetailsResponse= new UserDetailsResponse(1L,"Tarun","Bisht","tarun@gmail.com","hightarun");
        Assertions.assertEquals(userDetailsResponse.getId(),1L);
        Assertions.assertEquals(userDetailsResponse.getFirstName(),"Tarun");
        Assertions.assertEquals(userDetailsResponse.getLastName(),"Bisht");
        Assertions.assertEquals(userDetailsResponse.getUsername(),"hightarun");
        Assertions.assertEquals(userDetailsResponse.getEmail(),"tarun@gmail.com");

    }

    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(UserDetailsResponse.class);
    }
}
