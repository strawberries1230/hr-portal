package com.proj.authservice.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UserRole")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRole {
    @EmbeddedId
    private UserRoleKey userRoleKey;
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "roleId")
    private Role role;
}
