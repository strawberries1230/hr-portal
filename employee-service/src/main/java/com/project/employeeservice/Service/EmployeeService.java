package com.project.employeeservice.Service;

import com.project.employeeservice.DAO.EmployeeRepository;
import com.project.employeeservice.Entity.DTO.EmployeeDTO;
import com.project.employeeservice.Entity.Document.Employee;
import com.project.employeeservice.Entity.Model.Address;
import com.project.employeeservice.Entity.Model.VisaStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    public void createEmployee(EmployeeDTO employeeDTO) {
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
//    public void editEmployee(EmployeeDTO employeeDTO) {
//        Long employeeId = employeeDTO.getId();
//        Employee employee = employeeRepository.findById(employeeId).orElse(null);
//        if (employee == null) {
//            // 处理找不到员工的情况，比如抛出异常或返回错误信息
//            return;
//        }
//
//        if (employeeDTO.getFirstName() != null) {
//            employee.setFirstName(employeeDTO.getFirstName());
//        }
//
//        if (employeeDTO.getLastName() != null) {
//            employee.setLastName(employeeDTO.getLastName());
//        }
//
//        if (employeeDTO.getMiddleName() != null) {
//            employee.setMiddleName(employeeDTO.getMiddleName());
//        }
//
//        // 添加其他要编辑的字段的逻辑
//
//        employeeRepository.save(employee);
//    }


}
