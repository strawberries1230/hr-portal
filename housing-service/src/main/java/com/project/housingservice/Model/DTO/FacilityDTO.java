package com.project.housingservice.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FacilityDTO {
    private Long houseId;
    private String type;
    private String description;
    private Integer quantity;
}
