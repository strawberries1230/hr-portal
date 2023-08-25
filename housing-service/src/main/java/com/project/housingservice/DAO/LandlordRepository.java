package com.project.housingservice.DAO;

import com.project.housingservice.Model.Entity.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LandlordRepository extends JpaRepository<Landlord, Long> {
    Optional<Landlord> findById(Long id);
}
