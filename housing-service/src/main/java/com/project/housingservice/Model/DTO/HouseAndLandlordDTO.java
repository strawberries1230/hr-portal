package com.project.housingservice.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HouseAndLandlordDTO {
    private String address;
    private String landlordFullName;
    private String landlordPhone;
    private String landlordEmail;
}
