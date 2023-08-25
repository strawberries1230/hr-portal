package com.project.housingservice.DAO;

import com.project.housingservice.Model.Entity.Landlord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LandlordRepository extends JpaRepository<Landlord, Long> {
}
