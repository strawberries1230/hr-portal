package com.proj.authservice.Dao;

import com.proj.authservice.Model.Entity.UserRole;
import com.proj.authservice.Model.Entity.UserRoleKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleKey> {
}
