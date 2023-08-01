package com.proj.authservice.Service;

import com.proj.authservice.Dao.RoleRepository;
import com.proj.authservice.Model.Entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public Role findRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}
