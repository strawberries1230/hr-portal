package com.proj.authservice.Controller;

import com.proj.authservice.Exception.AlreadyExistsException;
import com.proj.authservice.Model.DTO.UserDTO;
import com.proj.authservice.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
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
    @GetMapping("/get")
    public ResponseEntity<?> getUser() {

        return ResponseEntity.ok("user get successfully");
    }
}
