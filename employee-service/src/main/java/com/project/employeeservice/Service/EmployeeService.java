package com.project.employeeservice.Service;

import com.project.employeeservice.DAO.EmployeeRepository;
import com.project.employeeservice.Entity.DTO.EmployeeDTO;
import com.project.employeeservice.Entity.Document.Employee;
import com.project.employeeservice.Entity.Model.*;
import com.project.employeeservice.Exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    public void createEmployee(String email, EmployeeDTO employeeDTO) {

        Employee employee = new Employee();
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employeeDTO.getLastName());
        employee.setMiddleName(employeeDTO.getMiddleName());
        employee.setPreferredName(employeeDTO.getPreferredName());
        employee.setSsn(employeeDTO.getSsn());
        employee.setDob(employeeDTO.getDob());
        employee.setGender(employeeDTO.getGender());
        employee.setContactInfo(employeeDTO.getContactInfo());
        employee.setDriverLicense(employeeDTO.getDriverLicense());
        employee.setEmergencyContact(employeeDTO.getEmergencyContact());

        employee.setEmail(email);

        Address address = employeeDTO.getAddress();
        if(address.getZipcode() != null) {
            address.setLastModificationDate(LocalDateTime.now());
            employee.setAddress(address);
        }
        VisaStatus visaStatus = employeeDTO.getVisaStatus();
        if(visaStatus.getVisaTitle() != null)  {
            visaStatus.setLastModificationDate(LocalDateTime.now());
            employee.setVisaStatus(employeeDTO.getVisaStatus());
        }
        employeeRepository.save(employee);
    }
    public void editEmployee(String email, EmployeeDTO employeeDTO) throws UserNotFoundException {
        Employee employee = employeeRepository.findByEmail(email);
        if(employee == null) {
            throw new UserNotFoundException("User not found with email: "+email);
        }

        if (employeeDTO.getFirstName() != null) {
            employee.setFirstName(employeeDTO.getFirstName());
        }

        if (employeeDTO.getLastName() != null) {
            employee.setLastName(employeeDTO.getLastName());
        }

        if (employeeDTO.getMiddleName() != null) {
            employee.setMiddleName(employeeDTO.getMiddleName());
        }
        if (employeeDTO.getPreferredName() != null) {
            employee.setPreferredName(employeeDTO.getPreferredName());
        }
        if (employeeDTO.getSsn() != null) {
            employee.setSsn(employeeDTO.getSsn());
        }
        if (employeeDTO.getDob() != null) {
            employee.setDob(employeeDTO.getDob());
        }
        if (employeeDTO.getGender() != null) {
            employee.setGender(employeeDTO.getGender());
        }
        if (employeeDTO.getAddress() != null) {
            Address address = employeeDTO.getAddress();
            if(!address.equals(employee.getAddress())) {
                address.setLastModificationDate(LocalDateTime.now());
                employee.setAddress(address);
            }
        }
        if (employeeDTO.getContactInfo() != null) {
            ContactInfo contactInfo = employeeDTO.getContactInfo();
            if(!contactInfo.equals(employee.getContactInfo())) {
                employee.setContactInfo(contactInfo);
            }
        }
        if (employeeDTO.getVisaStatus() != null) {
            VisaStatus visaStatus = employeeDTO.getVisaStatus();
            if(!visaStatus.equals(employee.getVisaStatus())) {
                visaStatus.setLastModificationDate(LocalDateTime.now());
                employee.setVisaStatus(visaStatus);
            }
        }
        if (employeeDTO.getEmergencyContact() != null) {
            EmergencyContact emergencyContact = employeeDTO.getEmergencyContact();
            if(!emergencyContact.equals(employee.getEmergencyContact())) {
                employee.setEmergencyContact(emergencyContact);
            }
        }

        if (employeeDTO.getDriverLicense() != null) {
            DriverLicense driverLicense = employeeDTO.getDriverLicense();
            if(!driverLicense.equals(employee.getDriverLicense())) {
                employee.setDriverLicense(driverLicense);
            }
        }

        employeeRepository.save(employee);
    }


}
