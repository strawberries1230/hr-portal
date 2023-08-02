package com.proj.authservice.Dao;

import com.proj.authservice.Model.Entity.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {
    Optional<RegistrationToken> findByEmailAndToken(String email, String token);

}
