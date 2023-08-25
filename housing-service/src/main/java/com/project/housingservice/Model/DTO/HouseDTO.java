package com.project.housingservice.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HouseDTO {

    private Long landlordId;
    private String address;
    private Integer maxOccupant;
    private Integer numOfResidents;
}
