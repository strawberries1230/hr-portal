package com.project.housingservice.Controller;

import com.project.housingservice.DAO.FacilityRepository;
import com.project.housingservice.DAO.HouseRepository;
import com.project.housingservice.DAO.LandlordRepository;
import com.project.housingservice.Exception.AccessDeniedException;
import com.project.housingservice.Exception.NotFoundException;
import com.project.housingservice.Model.DTO.*;
import com.project.housingservice.Model.Entity.Facility;
import com.project.housingservice.Model.Entity.House;
import com.project.housingservice.Service.FacilityService;
import com.project.housingservice.Service.HouseService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/housing")
public class HousingController {
    private final HouseService houseService;
    private final FacilityService facilityService;

    public HousingController(LandlordRepository landlordRepository, HouseRepository houseRepository, FacilityRepository facilityRepository, HouseService houseService, FacilityService facilityService) {
        this.houseService = houseService;
        this.facilityService = facilityService;
    }

    //HR add house
    @PostMapping("/create/house")
    public ResponseEntity<?> createHouse(@RequestHeader("X-User-Roles") String roles, @RequestBody HouseDTO houseDTO) throws AccessDeniedException, NotFoundException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        houseService.createHouse(houseDTO);

        return ResponseEntity.ok(String.format("house at %s saved!!!", houseDTO.getAddress()));
    }

    @PutMapping("/edit/house/{id}")
    public ResponseEntity<?> editeHouse(@RequestHeader("X-User-Roles") String roles, @PathVariable("id") Long houseId, @RequestBody HouseDTO houseDTO) throws AccessDeniedException, NotFoundException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        houseService.editHouse(houseId, houseDTO);
        return ResponseEntity.ok(String.format("house with id: %s edited!!!", houseId));
    }

    @DeleteMapping("/delete/house/{id}")
    public ResponseEntity<?> deleteHouse(@RequestHeader("X-User-Roles") String roles, @PathVariable("id") Long houseId) throws AccessDeniedException, NotFoundException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        houseService.deleteHouse(houseId);
        return ResponseEntity.ok(String.format("house with id: %s deleted!!!", houseId));
    }

    @GetMapping("/available/all")
    public ResponseEntity<?> findAvaliableHouse(@RequestHeader("X-User-Roles") String roles) throws AccessDeniedException, NotFoundException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        return ResponseEntity.ok(houseService.findAvaliableHouse());
    }

    @GetMapping("/available/{id}")
    public ResponseEntity<Boolean> checkAvailablity(@RequestHeader("X-User-Roles") String roles, @PathVariable("id") Long houseId) throws AccessDeniedException, NotFoundException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        boolean availability = houseService.checkHouseAvailablity(houseId);
        return ResponseEntity.ok(availability);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseDTO> findHouse(@RequestHeader("X-User-Roles") String roles, @PathVariable("id") Long houseId) throws AccessDeniedException, NotFoundException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        return ResponseEntity.ok(houseService.findHouse(houseId));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<HouseAndLandlordDTO> findHouseWithLandlord(@RequestHeader("X-User-Roles") String roles, @PathVariable("id") Long houseId) throws AccessDeniedException, NotFoundException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        return ResponseEntity.ok(houseService.findHouseWithLandlord(houseId));
    }

    @GetMapping("/total_residents")
    public ResponseEntity<String> getTotalNumOfResidents(@RequestHeader("X-User-Roles") String roles) throws AccessDeniedException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        return ResponseEntity.ok(String.format("Total number of employee residents: %d", houseService.getTotalNumOfResidents()));
    }

    @GetMapping("/house_name/{id}")
    public ResponseEntity<String> getHouseName(@RequestHeader("X-User-Roles") String roles, @PathVariable("id") String houseId) throws AccessDeniedException, NotFoundException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (roleList.isEmpty()) {
            throw new AccessDeniedException("Access denied, you need access");
        }
        Long house_id = Long.valueOf(houseId);
        return ResponseEntity.ok(houseService.getHouseName(house_id));
    }

    @PutMapping("/edit/residentnum/house/{id}")
    public ResponseEntity<String> changeResidentsNum(@RequestHeader("X-User-Roles") String roles, @PathVariable("id") Long houseId, @RequestParam Integer num, @RequestParam Boolean add) throws AccessDeniedException, NotFoundException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        houseService.changeResidentsNum(houseId, num, add);
        return ResponseEntity.ok(String.format("house with id: %s's resident number edited!!!", houseId));
    }

    @PostMapping("/create/facility")
    public ResponseEntity<?> createFacility(@RequestHeader("X-User-Roles") String roles, @RequestBody FacilityDTO facilityDTO) throws NotFoundException, AccessDeniedException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        facilityService.createFacility(facilityDTO);
        return ResponseEntity.ok("facility saved!!!");
    }
    @GetMapping("/house-summary/{id}")
    public ResponseEntity<?> getHouseSummary(@RequestHeader("X-User-Roles") String roles, @PathVariable("id") Long houseId) throws AccessDeniedException, NotFoundException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        FacilitySummaryDTO facilitySummaryDTO = houseService.getHouseSummary(houseId);
        return ResponseEntity.ok(facilitySummaryDTO);
    }
    @GetMapping("/house-reports/{id}")
    public ResponseEntity<?> viewHouseRelatedReports(@RequestHeader("X-User-Roles") String roles, @PathVariable("id") Long houseId, @RequestParam int page, @RequestParam int size) throws AccessDeniedException, NotFoundException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        Page<FacilityReportResponseDTO> facilityReportResponseDTOList = facilityService.viewHouseRelatedReports(houseId, page, size);
        return ResponseEntity.ok(facilityReportResponseDTOList.getContent());
    }

        @PostMapping("/facility")
    public ResponseEntity<?> createFacilityReport(@RequestHeader("X-User-Roles") String roles, @RequestHeader("X-User-Email") String email, @RequestBody FacilityReportDTO facilityReportDTO) throws AccessDeniedException, NotFoundException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_EMPLOYEE")) {
            throw new AccessDeniedException("Access denied, you need employee access");
        }
        facilityService.createFacilityReport(email, facilityReportDTO);
        return ResponseEntity.ok("successfully created report!!");
    }

    @PostMapping("/facility-report-comment")
    public ResponseEntity<?> createFacilityReportComment(@RequestHeader("X-User-Roles") String roles, @RequestHeader("X-User-Email") String email, @RequestBody FacilityReportCommentRequestDTO facilityReportCommentRequestDTO) throws AccessDeniedException, NotFoundException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (roleList.isEmpty()) {
            throw new AccessDeniedException("Access denied, you need access");
        }
        facilityService.createFacilityReportComment(email, facilityReportCommentRequestDTO);
        return ResponseEntity.ok("successfully created comment!!");
    }

    @GetMapping("/report/all")
    public ResponseEntity<List<FacilityReportResponseDTO>> viewAlleports(@RequestHeader("X-User-Roles") String roles) throws AccessDeniedException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        return ResponseEntity.ok(facilityService.viewAlleports());
    }

    @GetMapping("/report")
    public ResponseEntity<List<FacilityReportResponseDTO>> viewReportsByAuthor(@RequestHeader("X-User-Roles") String roles, @RequestHeader("X-User-Email") String email) throws AccessDeniedException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_EMPLOYEE")) {
            throw new AccessDeniedException("Access denied, you need employee access");
        }
        return ResponseEntity.ok(facilityService.viewReportsByAuthor(email));
    }

    @PutMapping("/edit/comment")
    public ResponseEntity<?> editComment(@RequestHeader("X-User-Roles") String roles, @RequestHeader("X-User-Email") String email, @RequestBody EditCommentDTO editCommentDTO) throws AccessDeniedException, NotFoundException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_EMPLOYEE")) {
            throw new AccessDeniedException("Access denied, you need employee access");
        }
        facilityService.editComment(email, editCommentDTO);
        return ResponseEntity.ok("successfully edited the comment!!!");
    }


    //    @PostMapping()
//    public ResponseEntity<?> createLandlord(@RequestBody LandlordDTO landlordDTO) {
//        Landlord landlord = new Landlord();
//        landlord.setFirstName(landlordDTO.getFirstName());
//        landlord.setLastName(landlordDTO.getLastName());
//        landlord.setEmail(landlordDTO.getEmail());
//        landlord.setPhone(landlordDTO.getPhone());
//        landlordRepository.save(landlord);
//        return ResponseEntity.ok("landlord saved!!!");
//    }
//    @PostMapping("/create/house")
//    public ResponseEntity<?> createLandlord(@RequestBody HouseDTO houseDTO) {
//        Landlord landlord = landlordRepository.findById(houseDTO.getLandlordId()).get();
//        House house = new House();
//        house.setLandlord(landlord);
//        house.setAddress(houseDTO.getAddress());
//        house.setMaxOccupant(houseDTO.getMaxOccupant());
//        house.setNumOfResidents(houseDTO.getNumOfResidents());
//        houseRepository.save(house);
//        return ResponseEntity.ok("house saved!!!");
//    }
//        @GetMapping("/landlord/{id}")
//        public ResponseEntity<?> getLandlordById(@PathVariable("id") Long landlordId) {
//            Landlord landlord = landlordRepository.findById(landlordId).get();
//
//            return ResponseEntity.ok("get landlord");
//        }

//    @PostMapping("/create/report")
//    ResponseEntity<?> createReport(HttpServletRequest request, @RequestBody FacilityReportDTO facilityReportDTO) {
//        List<String> roles = (List<String>) request.getAttribute("roles");
////        if (!roles.contains("ROLE_EMPLOYEE")) {
////            throw new AccessDeniedException("Access denied, you need employee access");
////        }
//        String email = (String) request.getAttribute("email");
//
//        return ResponseEntity.ok("You've started an application!");
//    }
}
