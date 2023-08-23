package com.project.applicationservice.Service;

import com.project.applicationservice.DAO.ApplicationRepository;
import com.project.applicationservice.Exception.NotFoundException;
import com.project.applicationservice.Model.PersonalApplication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
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
    public PersonalApplication editApplication(String email,String status, String comment) throws NotFoundException {
        Optional<PersonalApplication> personalApplicationOptional = applicationRepository.findByEmail(email);
        if(personalApplicationOptional.isEmpty()) {
            throw new NotFoundException(String.format("Application with email: %s is not found!",email));
        }
        PersonalApplication personalApplication = personalApplicationOptional.get();
        personalApplication.setStatus(status);
        personalApplication.setComment(comment);
        personalApplication.setLastModificationDate(LocalDate.now());
        return applicationRepository.save(personalApplication);
    }
    public Optional<PersonalApplication> findByEmail(String email) {
        return applicationRepository.findByEmail(email);
    }
}
