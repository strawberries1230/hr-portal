package com.proj.authservice.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "UserRole")
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userRoleKey")
public class UserRole {
    @EmbeddedId
    private UserRoleKey userRoleKey;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "userId")
    @JsonIdentityReference(alwaysAsId = true)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("roleId")
    @JoinColumn(name = "roleId")
    @JsonIdentityReference(alwaysAsId = true)
    private Role role;

    public UserRoleKey getUserRoleKey() {
        return userRoleKey;
    }

    public void setUserRoleKey(UserRoleKey userRoleKey) {
        this.userRoleKey = userRoleKey;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
