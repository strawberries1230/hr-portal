package com.project.housingservice.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FacilityReportResponseDTO {
    private String title;
    private String description;
    private String author;
    private LocalDateTime reportTime;
    private String status;
    private List<CommentResponseDTO> thread;
}
