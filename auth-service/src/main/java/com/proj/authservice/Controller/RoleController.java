package com.proj.authservice.Controller;

import com.proj.authservice.Dao.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
//        Role newRole = new Role();
//        newRole.setRoleName("HR");
//        newRole.setRoleDescription("hr, advanced permission");
//        newRole.setCreateDate(new Date());
//        newRole.setLastModificationDate(new Date());
//        roleRepository.save(newRole);
        return ResponseEntity.ok("role saved successfully");
    }
}
