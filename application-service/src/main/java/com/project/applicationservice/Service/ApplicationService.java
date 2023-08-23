package com.project.applicationservice.Service;

import com.project.applicationservice.DAO.ApplicationRepository;
import com.project.applicationservice.Model.PersonalApplication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }
    public void saveApplication(String email) {
        PersonalApplication personalApplication = new PersonalApplication();
        personalApplication.setEmail(email);
        personalApplication.setCreateDate(LocalDate.now());
        personalApplication.setLastModificationDate(LocalDate.now());
        personalApplication.setStatus("not submitted");
        personalApplication.setComment("");
        applicationRepository.save(personalApplication);
    }
}
