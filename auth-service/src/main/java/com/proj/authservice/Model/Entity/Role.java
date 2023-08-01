package com.proj.authservice.Model.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Role")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    @Column(unique = true, nullable = false)
    @NotBlank(message = "roleName is required")
    private String roleName;
    @Column(unique = true, nullable = false)
    @NotBlank(message = "roleDescription is required")
    private String roleDescription;
    @Column(nullable = false)
    private Date createDate;
    @Column(nullable = false)
    private Date lastModificationDate;
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<UserRole> userRoles = new HashSet<>();
}
