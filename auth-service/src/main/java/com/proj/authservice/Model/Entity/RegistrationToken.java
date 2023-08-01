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
@Data
public class RegistrationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    @Column(nullable = false)
    private String token;
    @ManyToOne
    @JoinColumn(name = "issuedBy")
    private User issuedBy;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Date expirationDate;
}
