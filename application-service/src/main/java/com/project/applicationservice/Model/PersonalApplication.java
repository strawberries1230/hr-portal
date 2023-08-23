package com.project.applicationservice.Model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "PersonalApplication")
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "email")
public class PersonalApplication {
    @Id
    private String email;
    @Column(nullable = false)
    private LocalDate createDate;
    @Column(nullable = false)
    private LocalDate lastModificationDate;
    @Column(nullable = false)
    private String status;
    private String comment;
    @OneToMany(mappedBy = "personalApplication", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    private Set<PersonalDocument> documents;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(LocalDate lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Set<PersonalDocument> getDocuments() {
        return documents;
    }

    public void setDocuments(Set<PersonalDocument> documents) {
        this.documents = documents;
    }
}