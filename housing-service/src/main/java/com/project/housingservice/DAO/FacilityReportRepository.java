package com.project.housingservice.DAO;

import com.project.housingservice.Model.Entity.FacilityReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityReportRepository extends JpaRepository<FacilityReport, Long> {
}
