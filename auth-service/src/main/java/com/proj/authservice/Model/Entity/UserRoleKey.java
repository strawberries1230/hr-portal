package com.proj.authservice.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.util.Objects;

@Embeddable
@Data
public class UserRoleKey {
    @Column(name = "userId")
    private Long userId;
    @Column(name = "roleId")
    private Long roleId;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoleKey that = (UserRoleKey) o;
        return Objects.equals(userId, that.userId) && Objects.equals(roleId, that.roleId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(userId, roleId);
    }
}
