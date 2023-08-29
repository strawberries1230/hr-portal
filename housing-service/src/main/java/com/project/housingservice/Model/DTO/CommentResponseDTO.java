package com.project.housingservice.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentResponseDTO {
    private String comment;
    private String author;
    private LocalDateTime lastModificationDateTime;
}
