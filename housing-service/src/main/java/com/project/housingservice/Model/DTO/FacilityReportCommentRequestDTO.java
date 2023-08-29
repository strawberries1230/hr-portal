package com.project.housingservice.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FacilityReportCommentRequestDTO {
    private Long facilityReportId;
    private String comment;
}
