package com.project.housingservice.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "FacilityReport")
@NoArgsConstructor
@AllArgsConstructor
public class FacilityReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "facilityId")
    @JsonIdentityReference(alwaysAsId = true)
    private Facility facility;
    @Column(nullable = false)
    private String createBy;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    private LocalDateTime createDateTime;
    @Column(nullable = false)
    private String status;

    @OneToMany(mappedBy = "facilityReport", cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    private List<FacilityReportComment> facilityReportComments;

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FacilityReportComment> getFacilityReportComments() {
        return facilityReportComments;
    }

    public void setFacilityReportComments(List<FacilityReportComment> facilityReportComments) {
        this.facilityReportComments = facilityReportComments;
    }
}
