package com.tweetapp.tweets.model.authentication;

import com.tweetapp.tweets.model.comment.Comment;
import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanTester;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResetPasswordTest {
    private ResetPassword resetPassword;

    private ResetPassword resetPassword2;

    @Test
    void testPensionerBean() {
        final BeanTester beanTester = new BeanTester();
        beanTester.getFactoryCollection();
        beanTester.testBean(ResetPassword.class);
    }
}
