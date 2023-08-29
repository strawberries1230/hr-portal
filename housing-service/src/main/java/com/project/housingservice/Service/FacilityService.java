package com.project.housingservice.Service;

import com.project.housingservice.DAO.FacilityReportCommentRepository;
import com.project.housingservice.DAO.FacilityReportRepository;
import com.project.housingservice.DAO.FacilityRepository;
import com.project.housingservice.DAO.HouseRepository;
import com.project.housingservice.Exception.AccessDeniedException;
import com.project.housingservice.Exception.NotFoundException;
import com.project.housingservice.Model.DTO.*;
import com.project.housingservice.Model.Entity.Facility;
import com.project.housingservice.Model.Entity.FacilityReport;
import com.project.housingservice.Model.Entity.FacilityReportComment;
import com.project.housingservice.Model.Entity.House;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacilityService {
    private final FacilityRepository facilityRepository;
    private final FacilityReportRepository facilityReportRepository;
    private final FacilityReportCommentRepository facilityReportCommentRepository;
    private final HouseRepository houseRepository;

    public FacilityService(FacilityRepository facilityRepository, FacilityReportRepository facilityReportRepository, FacilityReportCommentRepository facilityReportCommentRepository, HouseRepository houseRepository) {
        this.facilityRepository = facilityRepository;
        this.facilityReportRepository = facilityReportRepository;
        this.facilityReportCommentRepository = facilityReportCommentRepository;
        this.houseRepository = houseRepository;
    }

    public void createFacilityReport(String email, FacilityReportDTO facilityReportDTO) throws NotFoundException {
        Optional<Facility> facilityOptional = facilityRepository.findById(facilityReportDTO.getFacilityId());
        if (facilityOptional.isEmpty()) {
            throw new NotFoundException(String.format("Facility with id %s not found", facilityReportDTO.getFacilityId()));
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

    public List<FacilityReportResponseDTO> viewReportsByAuthor(String email) {
        List<FacilityReport> facilityReportList = facilityReportRepository.findByCreateBy(email);
        List<FacilityReportResponseDTO> facilityReportResponseDTOList = new ArrayList<>();
        for (FacilityReport facilityReport : facilityReportList) {
            FacilityReportResponseDTO facilityReportResponseDTO = new FacilityReportResponseDTO();
            facilityReportResponseDTO.setTitle(facilityReport.getTitle());
            facilityReportResponseDTO.setDescription(facilityReport.getDescription());
            facilityReportResponseDTO.setAuthor(facilityReport.getCreateBy());
            facilityReportResponseDTO.setReportTime(facilityReport.getCreateDateTime());
            facilityReportResponseDTO.setStatus(facilityReport.getStatus());

            List<FacilityReportComment> facilityReportCommentList = facilityReport.getFacilityReportComments();
            List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
            for (FacilityReportComment facilityReportComment : facilityReportCommentList) {
                CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
                commentResponseDTO.setAuthor(facilityReportComment.getCreateBy());
                commentResponseDTO.setComment(facilityReportComment.getComment());
                commentResponseDTO.setLastModificationDateTime(facilityReportComment.getLastModificationDateTime());
                commentResponseDTOList.add(commentResponseDTO);
            }
            facilityReportResponseDTO.setThread(commentResponseDTOList);
            facilityReportResponseDTOList.add(facilityReportResponseDTO);
        }
        return facilityReportResponseDTOList;
    }
    public Page<FacilityReportResponseDTO> viewHouseRelatedReports(Long houseId, int page, int size) throws NotFoundException {
        Optional<House> houseOptional = houseRepository.findById(houseId);
        if(houseOptional.isEmpty()) {
            throw new NotFoundException(String.format("House with id %s not found!", houseId));
        }
        List<Facility> facilities = houseOptional.get().getFacilities();
        List<Long> facilityIds = facilities.stream().map(Facility::getId).collect(Collectors.toList());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createDateTime").descending());


        // 这里你可以根据 facilityIds 来找到所有的 FacilityReport
        Page<FacilityReport> reports = facilityReportRepository.findByFacilityIdIn(facilityIds, pageable);

        List<FacilityReportResponseDTO> dtos = reports.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, reports.getTotalElements());

    }
    private FacilityReportResponseDTO convertToDto(FacilityReport facilityReport) {
        FacilityReportResponseDTO facilityReportResponseDTO = new FacilityReportResponseDTO();
        facilityReportResponseDTO.setTitle(facilityReport.getTitle());
        facilityReportResponseDTO.setDescription(facilityReport.getDescription());
        facilityReportResponseDTO.setAuthor(facilityReport.getCreateBy());
        facilityReportResponseDTO.setReportTime(facilityReport.getCreateDateTime());
        facilityReportResponseDTO.setStatus(facilityReport.getStatus());

        List<FacilityReportComment> facilityReportCommentList = facilityReport.getFacilityReportComments();
        List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
        for (FacilityReportComment facilityReportComment : facilityReportCommentList) {
            CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
            commentResponseDTO.setAuthor(facilityReportComment.getCreateBy());
            commentResponseDTO.setComment(facilityReportComment.getComment());
            commentResponseDTO.setLastModificationDateTime(facilityReportComment.getLastModificationDateTime());
            commentResponseDTOList.add(commentResponseDTO);
        }
        facilityReportResponseDTO.setThread(commentResponseDTOList);
       return facilityReportResponseDTO;
    }

    public List<FacilityReportResponseDTO> viewAlleports() {
        List<FacilityReport> facilityReportList = facilityReportRepository.findAll();
        List<FacilityReportResponseDTO> facilityReportResponseDTOList = new ArrayList<>();
        for (FacilityReport facilityReport : facilityReportList) {
            FacilityReportResponseDTO facilityReportResponseDTO = new FacilityReportResponseDTO();
            facilityReportResponseDTO.setTitle(facilityReport.getTitle());
            facilityReportResponseDTO.setDescription(facilityReport.getDescription());
            facilityReportResponseDTO.setAuthor(facilityReport.getCreateBy());
            facilityReportResponseDTO.setReportTime(facilityReport.getCreateDateTime());
            facilityReportResponseDTO.setStatus(facilityReport.getStatus());

            List<FacilityReportComment> facilityReportCommentList = facilityReport.getFacilityReportComments();
            List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
            for (FacilityReportComment facilityReportComment : facilityReportCommentList) {
                CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
                commentResponseDTO.setAuthor(facilityReportComment.getCreateBy());
                commentResponseDTO.setComment(facilityReportComment.getComment());
                commentResponseDTO.setLastModificationDateTime(facilityReportComment.getLastModificationDateTime());
                commentResponseDTOList.add(commentResponseDTO);
            }
            facilityReportResponseDTO.setThread(commentResponseDTOList);
            facilityReportResponseDTOList.add(facilityReportResponseDTO);
        }
        return facilityReportResponseDTOList;
    }
    public void createFacilityReportComment(String email, FacilityReportCommentRequestDTO facilityReportCommentRequestDTO) throws NotFoundException {
        Long facilityReportId = facilityReportCommentRequestDTO.getFacilityReportId();
        Optional<FacilityReport> facilityReportOptional = facilityReportRepository.findById(facilityReportId);
        if (facilityReportOptional.isEmpty()) {
            throw new NotFoundException(String.format("Facility report with id %s not found", facilityReportId));
        }
        FacilityReportComment facilityReportComment = new FacilityReportComment();
        facilityReportComment.setFacilityReport(facilityReportOptional.get());
        facilityReportComment.setComment(facilityReportCommentRequestDTO.getComment());
        facilityReportComment.setCreateBy(email);
        facilityReportComment.setCreateDateTime(LocalDateTime.now());
        facilityReportComment.setLastModificationDateTime(LocalDateTime.now());
        facilityReportCommentRepository.save(facilityReportComment);
    }
    public void editComment(String email, EditCommentDTO editCommentDTO) throws NotFoundException, AccessDeniedException {
        Long commentId = editCommentDTO.getCommentId();
        Optional<FacilityReportComment> facilityReportCommentOptional = facilityReportCommentRepository.findById(commentId);
        if(facilityReportCommentOptional.isEmpty()) {
            throw new NotFoundException(String.format("Comment with id %s not found", commentId));
        }
        FacilityReportComment facilityReportComment = facilityReportCommentOptional.get();
        if(!email.equals(facilityReportComment.getCreateBy())) {
            throw new AccessDeniedException("you need permission to edit the comment");
        }
        facilityReportComment.setLastModificationDateTime(LocalDateTime.now());
        facilityReportComment.setComment(editCommentDTO.getNewComment());
        facilityReportCommentRepository.save(facilityReportComment);
    }
    public void createFacility(FacilityDTO facilityDTO) throws NotFoundException {
        Optional<House> houseOptional = houseRepository.findById(facilityDTO.getHouseId());
        if(houseOptional.isEmpty()) {
            throw new NotFoundException(String.format("House with id %s not found!", facilityDTO.getHouseId()));
        }
        House house = houseOptional.get();
        Facility facility = new Facility();
        facility.setHouse(house);
        facility.setDescription(facilityDTO.getDescription());
        facility.setType(facilityDTO.getType());
        facility.setQuantity(facilityDTO.getQuantity());
        facilityRepository.save(facility);
    }



}
