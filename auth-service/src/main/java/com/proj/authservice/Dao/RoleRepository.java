package com.proj.authservice.Dao;

import com.proj.authservice.Model.Entity.Role;
import com.proj.authservice.Model.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);
}


