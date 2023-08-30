package com.project.applicationservice.Service;

import com.project.applicationservice.DAO.ApplicationRepository;
import com.project.applicationservice.Exception.DataFormatException;
import com.project.applicationservice.Exception.NotFoundException;
import com.project.basedomains.DTO.EmailEvent;
import com.project.applicationservice.Model.PersonalApplication;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final KafkaTemplate<String, EmailEvent> kafkaTemplate;

    private final NewTopic topic;

    public ApplicationService(ApplicationRepository applicationRepository, KafkaTemplate<String, EmailEvent> kafkaTemplate, KafkaTemplate<String, EmailEvent> kafkaTemplate1, NewTopic topic) {
        this.applicationRepository = applicationRepository;
        this.kafkaTemplate = kafkaTemplate1;
        this.topic = topic;

    }

    public void startApplication(String email) {
        PersonalApplication personalApplication = new PersonalApplication();
        personalApplication.setEmail(email);
        personalApplication.setCreateDate(LocalDate.now());
        personalApplication.setLastModificationDate(LocalDate.now());
        personalApplication.setStatus("not submitted");
        personalApplication.setComment("");
        applicationRepository.save(personalApplication);
    }

    public PersonalApplication editApplication(String email, String status, String comment) throws NotFoundException, DataFormatException {
        Optional<PersonalApplication> personalApplicationOptional = applicationRepository.findByEmail(email);
        if (personalApplicationOptional.isEmpty()) {
            throw new NotFoundException(String.format("Application with email: %s is not found!", email));
        }
        PersonalApplication personalApplication = personalApplicationOptional.get();
        personalApplication.setStatus(status);
        personalApplication.setComment(comment);
        personalApplication.setLastModificationDate(LocalDate.now());
        if ("rejected".equalsIgnoreCase(status)) {
            EmailEvent emailEvent = new EmailEvent();
            emailEvent.setTo(email);
            emailEvent.setSubject("Your Application Status");
            emailEvent.setBody("Your application has been rejected.");

            Message<EmailEvent> message = MessageBuilder
                    .withPayload(emailEvent)
                    .setHeader(KafkaHeaders.TOPIC, topic.name())
                    .build();
            kafkaTemplate.send(message);
//            kafkaTemplate.send("email_topic", emailMessage);
        }
        return applicationRepository.save(personalApplication);
    }

    public Optional<PersonalApplication> findByEmail(String email) {
        return applicationRepository.findByEmail(email);
    }
}
