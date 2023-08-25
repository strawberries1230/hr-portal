package com.project.employeeservice.Entity.DTO.externalDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HouseDTO {
    private Long landlordId;
    private String address;
    private Integer maxOccupant;
    private Integer numOfResidents;
}
