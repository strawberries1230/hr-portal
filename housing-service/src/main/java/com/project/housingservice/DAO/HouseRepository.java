package com.project.housingservice.DAO;

import com.project.housingservice.Model.Entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
}
