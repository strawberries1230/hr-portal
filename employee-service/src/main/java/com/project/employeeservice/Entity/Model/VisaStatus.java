package com.project.employeeservice.Entity.Model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class VisaStatus {
    private String visaTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isActiveFlag;
    private LocalDateTime lastModificationDate;

    public String getVisaTitle() {
        return visaTitle;
    }

    public void setVisaTitle(String visaTitle) {
        this.visaTitle = visaTitle;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isActiveFlag() {
        return isActiveFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        isActiveFlag = activeFlag;
    }

    public LocalDateTime getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(LocalDateTime lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VisaStatus that = (VisaStatus) o;
        return isActiveFlag == that.isActiveFlag && Objects.equals(visaTitle, that.visaTitle) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visaTitle, startDate, endDate, isActiveFlag);
    }
}
