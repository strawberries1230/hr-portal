package com.project.employeeservice.Controller;

import com.project.employeeservice.Entity.DTO.EmployeeDTO;
import com.project.employeeservice.Entity.Document.Employee;
import com.project.employeeservice.Entity.Model.*;
import com.project.employeeservice.Mapper.EmployeeMapper;
import com.project.employeeservice.Service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping()
    public ResponseEntity<?> get() {
        return new ResponseEntity<>("GOOOOOOOOOD!", HttpStatus.OK);
    }

    @PostMapping("/create")
    public Employee createUser(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setMiddleName(employeeDTO.getMiddleName());
        employee.setPreferredName(employeeDTO.getPreferredName());
        employee.setProfilePicture(employeeDTO.getProfilePicture());
        employee.setSsn(employeeDTO.getSsn());
        employee.setDob(employeeDTO.getDob());
        employee.setGender(employeeDTO.getGender());
        employee.setAddress(employeeDTO.getAddress());
        employee.setContactInfo(employeeDTO.getContactInfo());
        employee.setDriverLicense(employeeDTO.getDriverLicense());
        employee.setVisaStatus(employeeDTO.getVisaStatus());
        employee.setEmergencyContact(employeeDTO.getEmergencyContact());


        return employeeService.createEmployee(employee);
    }
//    @PostMapping("/create")
//    public String createUser(@RequestBody EmployeeDTO employeeDTO) {
//
//        return employeeDTO.getFirstName();
//    }


}
