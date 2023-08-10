package com.project.applicationservice.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "PersonalDocument")
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PersonalDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private boolean isRequired;
    private String path;
    private String description;
    private String status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "email",insertable = false, updatable = false)
    @JsonIdentityReference(alwaysAsId = true)
    private PersonalApplication personalApplication;

}