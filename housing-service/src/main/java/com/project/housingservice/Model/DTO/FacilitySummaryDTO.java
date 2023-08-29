package com.project.housingservice.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FacilitySummaryDTO {
    private Integer NumberofBeds;
    private Integer NumberofMattresses;
    private Integer NumberofTables;
    private Integer NumberofChairs;
}
