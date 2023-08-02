package com.proj.authservice.Controller;

import com.proj.authservice.Auth.JwtUtil;
import com.proj.authservice.Exception.AlreadyExistsException;
import com.proj.authservice.Exception.EmailNotFoundException;
import com.proj.authservice.Exception.InvalidCredentialsException;
import com.proj.authservice.Exception.UserNotFoundException;
import com.proj.authservice.Model.DTO.UserDTO;
import com.proj.authservice.Model.Request.LoginRequest;
import com.proj.authservice.Model.Request.LoginResponse;
import com.proj.authservice.Service.UserService;
import com.proj.authservice.Util.EmailValidator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) throws AlreadyExistsException {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        userService.registerUser(userDTO);
        return ResponseEntity.ok("user registered successfully");
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) throws InvalidCredentialsException{
        LoginResponse loginResponse = userService.loginUser(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
    @PreAuthorize("hasRole('ROLE_HR')")
    @GetMapping
    public String accessSecureEndpoint() {
        return "You have access to this secure endpoint as HR";
    }
    @PreAuthorize("hasRole('ROLE_HR')")
    @PostMapping("/generate-link/{email}")
    public ResponseEntity<?> generateRegistrationLink(@PathVariable("email") String email) throws UserNotFoundException, EmailNotFoundException {
        if(!EmailValidator.validate(email)) {
            return ResponseEntity.badRequest().body("Invalid email address!");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        userService.generateRegistrationLink(username, email);


        return ResponseEntity.ok("You have successfully sent the registration link to " + email);
    }


}
