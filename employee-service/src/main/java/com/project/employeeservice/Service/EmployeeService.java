package com.project.employeeservice.Service;

import com.project.employeeservice.DAO.EmployeeRepository;
import com.project.employeeservice.Entity.Document.Employee;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

}
