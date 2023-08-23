package com.project.applicationservice.Controller;

import com.project.applicationservice.Exception.AccessDeniedException;
import com.project.applicationservice.Exception.AlreadyExistsException;
import com.project.applicationservice.Exception.NotFoundException;
import com.project.applicationservice.Model.PersonalApplication;
import com.project.applicationservice.Service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
//    @GetMapping()
//    ResponseEntity<?> get() {
//        return ResponseEntity.ok("you get it!");
//    }

    @PostMapping("/create")
    ResponseEntity<?> startApplication(HttpServletRequest request) throws AccessDeniedException, AlreadyExistsException {
        List<String> roles = (List<String>) request.getAttribute("roles");
        if (!roles.contains("ROLE_EMPLOYEE")) {
            throw new AccessDeniedException("Access denied, you need employee access");
        }
        String email = (String) request.getAttribute("email");
        if(applicationService.findByEmail(email)) {
            throw new AlreadyExistsException(String.format("application already exists with email: %s",email));
        }
        applicationService.startApplication(email);
        return ResponseEntity.ok("You've started an application!");
    }
    @PutMapping("/{email}")
    ResponseEntity<?> editApplication(HttpServletRequest request,@PathVariable String email, @RequestParam String status,@RequestParam String comment) throws AccessDeniedException, NotFoundException {
        List<String> roles = (List<String>) request.getAttribute("roles");
        if (!roles.contains("ROLE_HR")) {
            throw new AccessDeniedException("Access denied, you need hr access");
        }
        PersonalApplication personalApplication = applicationService.editApplication(email,status,comment);

        return ResponseEntity.ok(personalApplication);
    }

}
