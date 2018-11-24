package com.ALCverificationtool.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailSettings {

    @Value("${spring.mail.host}")
    private String mailSenderHost;

    @Value("${spring.mail.port}")
    private int mailSenderPort;

    @Value("${spring.mail.username}")
    private String mailSenderUsername;

    @Value("${spring.mail.password}")
    private String mailSenderPassword;

    @Value("${spring.mail.properties.mail.transport.protocol}")
    private String mailSenderPropertiesProtocol;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String mailSenderPropertiesAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String mailSenderPropertiesStart;

    @Value("${spring.mail.properties.mail.mail.debug}")
    private String mailSenderPropertiesDebug;

    @Bean
    public JavaMailSender getJavaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailSenderHost);
        mailSender.setPort(mailSenderPort);

        mailSender.setUsername(mailSenderUsername);
        mailSender.setPassword(mailSenderPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", mailSenderPropertiesProtocol);
        props.put("mail.smtp.auth", mailSenderPropertiesAuth);
        props.put("mail.smtp.starttls.enable", mailSenderPropertiesStart);
        props.put("mail.debug",mailSenderPropertiesDebug);

        return mailSender;
    }
}
