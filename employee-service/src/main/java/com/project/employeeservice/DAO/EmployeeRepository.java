package com.project.employeeservice.DAO;

import com.project.employeeservice.Entity.Document.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Employee findByEmail(String email);
    List<Employee> findByHouseId(String houseId);
    List<Employee> findByVisaStatus_IsActiveFlag(Boolean activeFlag);

}
