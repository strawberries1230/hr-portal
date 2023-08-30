package com.project.emailservice.Kafka;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.project.basedomains.DTO.EmailEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Properties;


@Service
public class EmailConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailConsumer.class);
    @Value("${client.id}")
    private String CLIENT_ID;
    @Value("${client.secret}")
    private String CLIENT_SECRET;
    @Value("${refresh.token}")
    private String REFRESH_TOKEN;

    @KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(EmailEvent message) {
        LOGGER.info(String.format("Email Sending....."));
        sendEmail(message.getSubject(), message.getBody());
    }


    private Gmail createGmailService() throws Exception {
        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(GoogleNetHttpTransport.newTrustedTransport())
                .setJsonFactory(JacksonFactory.getDefaultInstance())
                .setClientSecrets(CLIENT_ID, CLIENT_SECRET)
                .build()
                .setRefreshToken(REFRESH_TOKEN);
        // check if token needs to be refreshed
        if (credential.getExpiresInSeconds() == null || credential.getExpiresInSeconds() <= 60) {
            boolean refreshed = credential.refreshToken();
            if (!refreshed) {
                LOGGER.error("Failed to refresh the token.");
            }
        }
        Gmail service = new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(), JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName("My email service")
                .build();

        return service;
    }

    public void sendEmail(String subject, String body) {
        try {
            Gmail service = createGmailService();
            MimeMessage emailContent = createEmail("mian.dai.work@gmail.com", subject, body);
            sendMessage(service, "me", emailContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MimeMessage createEmail(String to, String subject, String bodyText) throws Exception {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress("mian.dai.work@gmail.com"));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);

        return email;
    }

    private void sendMessage(Gmail service, String userId, MimeMessage emailContent) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] bytes = buffer.toByteArray();
        String encodedEmail = Base64.getEncoder().encodeToString(bytes);

        Message message = new Message();
        message.setRaw(encodedEmail);
        System.out.println("aaqa!!!");

        service.users().messages().send(userId, message).execute();
    }

}
