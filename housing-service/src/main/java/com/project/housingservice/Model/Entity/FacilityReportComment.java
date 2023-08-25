package com.project.housingservice.Model.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "FacilityReportComment")
@NoArgsConstructor
@AllArgsConstructor
public class FacilityReportComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "facilityReportId")
    @JsonIdentityReference(alwaysAsId = true)
    private FacilityReport facilityReport;
    @Column(nullable = false)
    private String createBy;
    private String comment;
    private LocalDateTime createDateTime;
    private LocalDateTime lastModificationDateTime;

    public FacilityReport getFacilityReport() {
        return facilityReport;
    }

    public void setFacilityReport(FacilityReport facilityReport) {
        this.facilityReport = facilityReport;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public LocalDateTime getLastModificationDateTime() {
        return lastModificationDateTime;
    }

    public void setLastModificationDateTime(LocalDateTime lastModificationDateTime) {
        this.lastModificationDateTime = lastModificationDateTime;
    }
}
