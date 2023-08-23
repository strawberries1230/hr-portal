package com.project.applicationservice.DAO;

import com.project.applicationservice.Model.PersonalApplication;
import com.project.applicationservice.Model.PersonalDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DocumentRepository extends JpaRepository<PersonalDocument, Long> {
    Optional<PersonalDocument> findByEmailAndType(String email, String type);
}
