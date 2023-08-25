package com.project.housingservice.DAO;

import com.project.housingservice.Model.Entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Column;
import java.util.List;
import java.util.Optional;

public interface HouseRepository extends JpaRepository<House, Long> {
    Optional<House> findById(Long id);
    @Query("SELECT h FROM House h WHERE h.maxOccupant > h.numOfResidents")
    List<House> findHousesWithSpace();
}
