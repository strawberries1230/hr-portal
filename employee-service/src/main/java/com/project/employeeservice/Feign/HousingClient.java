package com.project.employeeservice.Feign;

import com.project.employeeservice.Entity.DTO.externalDTO.HouseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@FeignClient(name = "HOUSING-SERVICE")
public interface HousingClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/housing/available/{id}")
    ResponseEntity<Boolean> checkAvailablity(@RequestHeader("X-User-Roles") String roles, @PathVariable("id") Long houseId);
    @RequestMapping(method = RequestMethod.PUT, value = "/api/housing/edit/residentnum/house/{id}")
    ResponseEntity<String> changeResidentsNum(@RequestHeader("X-User-Roles") String roles, @PathVariable("id") Long houseId, @RequestParam Integer num, @RequestParam Boolean add);
    @RequestMapping(method = RequestMethod.GET, value = "/api/housing/house_name/{id}")
    ResponseEntity<String> getHouseName(@RequestHeader("X-User-Roles") String roles, @PathVariable("id") String houseId);
}
