package com.project.applicationservice.DAO;

import com.project.applicationservice.Model.PersonalApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<PersonalApplication, String> {
    Optional<PersonalApplication> findByEmail(String email);
}
