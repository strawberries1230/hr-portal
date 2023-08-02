package com.proj.authservice.Dao;

import com.proj.authservice.Model.Entity.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {
}
