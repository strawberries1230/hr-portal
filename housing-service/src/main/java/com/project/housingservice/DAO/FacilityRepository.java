package com.project.housingservice.DAO;

import com.project.housingservice.Model.Entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    void deleteAllByHouseId(Long houseId);
}
