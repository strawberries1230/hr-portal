package com.project.employeeservice.DAO;

import com.project.employeeservice.Entity.Document.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {
    Employee findByEmail(String email);
}
