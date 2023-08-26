package com.project.housingservice.Service;

import com.project.housingservice.DAO.FacilityReportCommentRepository;
import com.project.housingservice.DAO.FacilityReportRepository;
import com.project.housingservice.DAO.FacilityRepository;
import com.project.housingservice.Exception.NotFoundException;
import com.project.housingservice.Model.DTO.FacilityReportDTO;
import com.project.housingservice.Model.Entity.Facility;
import com.project.housingservice.Model.Entity.FacilityReport;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final FacilityReportRepository facilityReportRepository;
    private final FacilityReportCommentRepository facilityReportCommentRepository;

    public FacilityService(FacilityRepository facilityRepository, FacilityReportRepository facilityReportRepository, FacilityReportCommentRepository facilityReportCommentRepository) {
        this.facilityRepository = facilityRepository;
        this.facilityReportRepository = facilityReportRepository;
        this.facilityReportCommentRepository = facilityReportCommentRepository;
    }
    public void createFacilityReport(String email,FacilityReportDTO facilityReportDTO) throws NotFoundException {
        Optional<Facility> facilityOptional = facilityRepository.findById(facilityReportDTO.getFacilityId());
        if(facilityOptional.isEmpty()) {
            throw new NotFoundException(String.format("Facility with id %s not found",facilityReportDTO.getFacilityId()));
        }
        FacilityReport facilityReport = new FacilityReport();
        facilityReport.setFacility(facilityOptional.get());
        facilityReport.setCreateBy(email);
        facilityReport.setDescription(facilityReportDTO.getDescription());
        facilityReport.setStatus("OPEN");
        facilityReport.setTitle(facilityReportDTO.getTitle());
        facilityReport.setCreateDateTime(LocalDateTime.now());
        facilityReportRepository.save(facilityReport);
    }
}
