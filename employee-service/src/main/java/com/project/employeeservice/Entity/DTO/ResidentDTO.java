package com.project.employeeservice.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResidentDTO {
    private String fullName;
    private String phone;
    private String email;
}
