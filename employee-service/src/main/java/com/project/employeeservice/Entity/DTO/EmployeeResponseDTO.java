package com.project.employeeservice.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeResponseDTO {
    private String name;
    private String visaTitle;
    private LocalDate endDate;
    private Long daysLeft;
}
