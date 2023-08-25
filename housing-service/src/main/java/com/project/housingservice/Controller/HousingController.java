package com.project.housingservice.Controller;

import com.project.housingservice.DAO.FacilityRepository;
import com.project.housingservice.DAO.HouseRepository;
import com.project.housingservice.DAO.LandlordRepository;
import com.project.housingservice.Exception.AccessDeniedException;
import com.project.housingservice.Exception.NotFoundException;
import com.project.housingservice.Model.DTO.HouseDTO;
import com.project.housingservice.Service.HouseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/housing")
public class HousingController {
    private final HouseService houseService;

    public HousingController(LandlordRepository landlordRepository, HouseRepository houseRepository, FacilityRepository facilityRepository, HouseService houseService) {
        this.houseService = houseService;
    }

    //HR add house
    @PostMapping("/create/house")
    public ResponseEntity<?> createHouse(@RequestHeader("X-User-Roles") String roles, @RequestBody HouseDTO houseDTO) throws AccessDeniedException, NotFoundException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        //String email = (String) request.getAttribute("email");
        houseService.createHouse(houseDTO);

        return ResponseEntity.ok(String.format("house at %s saved!!!", houseDTO.getAddress()));
    }

    //HR edit house
    @DeleteMapping("/delete/house/{id}")
    public ResponseEntity<?> deleteHouse(@RequestHeader("X-User-Roles") String roles, @PathVariable("id") Long houseId) throws AccessDeniedException, NotFoundException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        houseService.deleteHouse(houseId);
        return ResponseEntity.ok(String.format("house with id: %s deleted!!!", houseId));
    }

    @GetMapping("/available")
    public ResponseEntity<?> findAvaliableHouse(@RequestHeader("X-User-Roles") String roles) throws AccessDeniedException, NotFoundException {
        List<String> roleList = Arrays.asList(roles.split(","));
        if (!roleList.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        //houseService.findAvaliableHouse();
        return ResponseEntity.ok(houseService.findAvaliableHouse());
    }


//    @GetMapping()
//    public ResponseEntity<?> test() {
//        return ResponseEntity.ok("ok!!!");
//    }

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
//    @PostMapping("/create/facility")
//    public ResponseEntity<?> createFacility(@RequestBody FacilityDTO facilityDTO) {
//        House house = houseRepository.findById(facilityDTO.getHouseId()).get();
//        Facility facility = new Facility();
//        facility.setHouse(house);
//        facility.setDescription(facilityDTO.getDescription());
//        facility.setType(facilityDTO.getType());
//        facility.setQuantity(facilityDTO.getQuantity());
//        facilityRepository.save(facility);
//        return ResponseEntity.ok("facility saved!!!");
//    }
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
