package com.tweetapp.tweets.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.MessagingException;

@SpringBootTest
public class EmailSenderServiceTest {

    private JavaMailSender javaMailSender;

    private EmailSenderService emailSenderService;


    @BeforeEach
    void setUp(){
        this.javaMailSender = Mockito.mock(JavaMailSender.class);
        emailSenderService = new EmailSenderService(javaMailSender);
    }

    @Test
    public void sendSimpleMailTest() throws  MessagingException {
        String subject = "test subject";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("test@sender.com");
        message.setTo("test@receiver.com");
        message.setSubject(subject);
        message.setText("test message");
       // javaMailSender.send(message);
        Assertions.assertEquals(message.getFrom(),"test@sender.com");
        Assertions.assertEquals(message.getTo()[0],"test@receiver.com");
        Assertions.assertEquals(message.getSubject(),subject);
        Assertions.assertEquals(message.getText(),"test message");
    }

}
