package com.project.applicationservice.Controller;

import com.project.applicationservice.Service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    @GetMapping()
    ResponseEntity<?> get() {
        return ResponseEntity.ok("you get it!");
    }
    @PostMapping("/{email}")
    ResponseEntity<?> startApplication(@PathVariable("email") String email) {
        applicationService.saveApplication(email);
        return ResponseEntity.ok("good!");
    }
}
