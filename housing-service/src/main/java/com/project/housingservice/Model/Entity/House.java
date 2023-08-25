package com.project.housingservice.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "House")
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "landlordId")
    @JsonIdentityReference(alwaysAsId = true)
    private Landlord landlord;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private Integer maxOccupant;
    @Column(nullable = false)
    private Integer numOfResidents;

    public Landlord getLandlord() {
        return landlord;
    }

    public void setLandlord(Landlord landlord) {
        this.landlord = landlord;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getMaxOccupant() {
        return maxOccupant;
    }

    public void setMaxOccupant(Integer maxOccupant) {
        this.maxOccupant = maxOccupant;
    }

    public Integer getNumOfResidents() {
        return numOfResidents;
    }

    public void setNumOfResidents(Integer numOfResidents) {
        this.numOfResidents = numOfResidents;
    }
}

