package com.project.employeeservice.Entity.DTO.externalDTO;

import com.project.employeeservice.Entity.DTO.ResidentDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HouseResidentsInfoDTO {
    private List<ResidentDTO> residents;
    private Integer numOfResidents;
}
