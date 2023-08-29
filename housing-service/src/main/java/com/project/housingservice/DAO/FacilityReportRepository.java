package com.project.housingservice.DAO;

import com.project.housingservice.Model.Entity.FacilityReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityReportRepository extends JpaRepository<FacilityReport, Long> {
    List<FacilityReport> findByCreateBy(String email);
    Page<FacilityReport> findByFacilityIdIn(List<Long> facilityId, Pageable pageable);
}
