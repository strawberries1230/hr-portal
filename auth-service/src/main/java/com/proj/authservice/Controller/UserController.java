package com.proj.authservice.Controller;

import com.proj.authservice.Auth.JwtUtil;
import com.proj.authservice.Exception.AlreadyExistsException;
import com.proj.authservice.Exception.InvalidCredentialsException;
import com.proj.authservice.Model.DTO.UserDTO;
import com.proj.authservice.Model.Request.LoginRequest;
import com.proj.authservice.Model.Request.LoginResponse;
import com.proj.authservice.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
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

    @GetMapping("/get")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    public ResponseEntity<?> get(){
       // LoginResponse loginResponse = userService.loginUser(loginRequest);
        return ResponseEntity.ok("you are hr");
    }
//    public void register(String email, Long issuedById) {
//        User issuedBy = userRepository.findById(issuedById).orElseThrow();
//
//        RegistrationToken token = new RegistrationToken();
//        token.setToken(UUID.randomUUID().toString()); // 创建一个新的UUID作为令牌
//        token.setEmail(email);
//        token.setIssuedBy(issuedBy);
//        token.setExpiryDate(OffsetDateTime.now().plusDays(1)); // 设置令牌过期时间
//
//        registrationTokenRepository.save(token);
//
//        String tokenLink = "http://your-app.com/register?token=" + token.getToken(); // 创建令牌链接，你需要替换为你的应用的实际URL
//        emailService.sendEmail(email, "Registration", "Click this link to register: " + tokenLink); // 发送邮件
//    }

}
