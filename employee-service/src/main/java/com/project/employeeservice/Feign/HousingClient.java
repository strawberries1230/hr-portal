package com.project.employeeservice.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

//@FeignClient(name = "HOUSING-SERVICE")
public interface HousingClient {
//    @RequestMapping(method = RequestMethod.PUT, value = "/api/housing/edit/house/{id}")
//    <T> T editHouse(HttpServletRequest request, @PathVariable("id") Long houseId, @RequestBody HouseDTO houseDTO);

}
