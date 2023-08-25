package com.project.housingservice.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/housing")
public class HousingController {
    @GetMapping()
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("ok!!!");
    }
}
