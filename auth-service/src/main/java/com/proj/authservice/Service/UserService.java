package com.proj.authservice.Service;

import com.proj.authservice.Auth.JwtUtil;
import com.proj.authservice.Dao.RegistrationTokenRepository;
import com.proj.authservice.Dao.RoleRepository;
import com.proj.authservice.Dao.UserRepository;
import com.proj.authservice.Dao.UserRoleRepository;
import com.proj.authservice.Exception.*;
import com.proj.authservice.Model.DTO.UserDTO;
import com.proj.authservice.Model.Entity.*;
import com.proj.authservice.Model.Request.LoginRequest;
import com.proj.authservice.Model.Request.LoginResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final RegistrationTokenRepository registrationTokenRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;



    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository, RegistrationTokenRepository registrationTokenRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.registrationTokenRepository = registrationTokenRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse loginUser(LoginRequest loginRequest) throws InvalidCredentialsException {
        try {
            // Try to authenticate the user
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword()
            );
            Authentication authenticated = authenticationManager.authenticate(auth);
            // If authentication is successful, generate a token
            UserDetails userDetails = (UserDetails) authenticated.getPrincipal();
            Collection<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            System.out.println("roles: "+ roles);
           String token = jwtUtil.generateToken(userDetails, roles);
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(secret)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//            List<String> tokenRoles = claims.get("roles", List.class);
//            System.out.println("Token roles: " + tokenRoles);

            return new LoginResponse(token);
        } catch (Exception e) {
            throw new InvalidCredentialsException("Invalid credentials provided.");
        }

    }


    public void registerUser(UserDTO userDTO) throws AlreadyExistsException {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new AlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new AlreadyExistsException("Email already exists");
        }

        String hashedPassword = passwordEncoder.encode(userDTO.getPassword());
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(hashedPassword);
        newUser.setCreateDate(new Date());
        newUser.setLastModificationDate(new Date());
        newUser.setActiveFlag(true);
        userRepository.save(newUser);

        Role role = roleRepository.findByRoleName("EMPLOYEE");

        UserRole userRole = new UserRole();
        UserRoleKey userRoleKey = new UserRoleKey();
        userRoleKey.setUserId(newUser.getUserId());
        userRoleKey.setRoleId(role.getRoleId());
        userRole.setUserRoleKey(userRoleKey);
        userRole.setUser(newUser);
        userRole.setRole(role);

        userRoleRepository.save(userRole);
        // 新增日志输出，确认关联关系是否正确
//        System.out.println("Saved User: " + newUser.getUsername());
//        System.out.println("Saved UserRole: " + userRole.getUser().getUsername() + " - " + userRole.getRole().getRoleName());

    }
    public void generateRegistrationLink(String username, String email) throws UserNotFoundException {

        User issuedByUser = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        //User newEmployee = userRepository.findByEmail(email).orElseThrow(() -> new EmailNotFoundException("User not found with email: " + email));

        RegistrationToken registrationToken = new RegistrationToken();
        registrationToken.setToken(UUID.randomUUID().toString());
        registrationToken.setEmail(email);
        //registrationToken.setNewEmployee(newEmployee);
        registrationToken.setIssuedByUser(issuedByUser);

        OffsetDateTime offsetDateTime = OffsetDateTime.now().plusDays(7);
        Date expirationDate = Date.from(offsetDateTime.toInstant());

        registrationToken.setExpirationDate(expirationDate);
        registrationTokenRepository.save(registrationToken);
    }
    public void checkToken(String email, String token) throws TokenNotFoundException, TokenExpiredException {
        RegistrationToken registrationToken = registrationTokenRepository.findByEmailAndToken(email, token).orElseThrow(() -> new TokenNotFoundException("Token Not Found."));
        Date expirationDate = registrationToken.getExpirationDate();
        if(expirationDate.before(new Date())) {
            throw new TokenExpiredException("Token has expired.");
        }
    }

}
