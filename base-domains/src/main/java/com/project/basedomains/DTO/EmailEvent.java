package com.project.basedomains.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailEvent {
    private String to;
    private String subject;
    private String body;
}
