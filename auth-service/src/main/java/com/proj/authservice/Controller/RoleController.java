package com.proj.authservice.Controller;

import com.proj.authservice.Dao.RoleRepository;
import com.proj.authservice.Model.Entity.Role;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping("/create")
    public ResponseEntity<?> createRole() {
        Role newRole = new Role();
        newRole.setRoleName("EMPLOYEE");
        newRole.setRoleDescription("employee, basic permission");
        newRole.setCreateDate(new Date());
        newRole.setLastModificationDate(new Date());
        roleRepository.save(newRole);
        return ResponseEntity.ok("role saved successfully");
    }
}
