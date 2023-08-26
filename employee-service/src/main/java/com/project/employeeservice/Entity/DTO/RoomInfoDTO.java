package com.project.employeeservice.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomInfoDTO {
    private List<RoommateDTO> roommates;
    private String address;
}
