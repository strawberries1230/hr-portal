package com.proj.authservice.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "RegistrationToken")
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    @Column(nullable = false)
    private String token;
    @Column(name = "issuedBy")
    private String issuedBy;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Date expirationDate;
    @ManyToOne
    @JoinColumn(name = "issuedBy", referencedColumnName="username", insertable = false, updatable = false)
    private User issuedByUser;

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIssuedBy() {
        return issuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        this.issuedBy = issuedBy;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User getIssuedByUser() {
        return issuedByUser;
    }

    public void setIssuedByUser(User issuedByUser) {
        this.issuedByUser = issuedByUser;
    }
}
