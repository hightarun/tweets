package com.tweetapp.tweets.model.authentication;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtUserDetailsTest {

    private JwtUserDetails jwtUserDetails;

    @BeforeEach
    void setUp(){
        jwtUserDetails = new JwtUserDetails(new User(1L, "Tarun", "Bisht", "tarun@gmail.com", "hightarun", "12345678", "8929409364", null));
    }

    @Test
    void test(){
        assertThat(jwtUserDetails.getUsername().equals("hightarun"));
        assertThat(jwtUserDetails.getPassword().equals("12345678"));
        assertThat(jwtUserDetails.getAuthorities().equals(true));
        assertThat(jwtUserDetails.isAccountNonExpired()).isTrue();
        assertThat(jwtUserDetails.isAccountNonLocked()).isTrue();
        assertThat(jwtUserDetails.isCredentialsNonExpired()).isTrue();
        assertThat(jwtUserDetails.isEnabled()).isTrue();
    }

    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(JwtLoginRequest.class);
    }
}
