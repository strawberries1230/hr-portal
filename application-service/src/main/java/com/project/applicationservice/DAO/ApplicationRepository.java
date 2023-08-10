package com.project.applicationservice.DAO;

import com.project.applicationservice.Model.PersonalApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<PersonalApplication, String> {
}
