package com.proj.authservice.Service;

import com.proj.authservice.Dao.RoleRepository;
import com.proj.authservice.Dao.UserRepository;
import com.proj.authservice.Dao.UserRoleRepository;
import com.proj.authservice.Exception.AlreadyExistsException;
import com.proj.authservice.Model.DTO.UserDTO;
import com.proj.authservice.Model.Entity.Role;
import com.proj.authservice.Model.Entity.User;
import com.proj.authservice.Model.Entity.UserRole;
import com.proj.authservice.Model.Entity.UserRoleKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
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
    }
}
