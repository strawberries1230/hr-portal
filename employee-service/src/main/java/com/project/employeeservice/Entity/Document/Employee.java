package com.project.employeeservice.Entity.Document;

import com.project.employeeservice.Entity.Model.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "Employee")
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String preferredName;
    private String profilePicture;
    private String email;
    private String ssn;
    private LocalDate dob;
    private String gender;
    private String houseId;
    private Address address;
    private ContactInfo contactInfo;
    private VisaStatus visaStatus;
    private EmergencyContact emergencyContact;
    private DriverLicense driverLicense;
//    private List<String> personalDocuments = new ArrayList<>();

    public void setEmail(String email) {
        this.email = email;
    }

    public Employee(String firstName, String lastName) {
    }

    public Employee(String firstName, String lastName, String middleName, String preferredName, String gender, String profilePicture, String ssn, Address address, LocalDate dob, ContactInfo contactInfo, DriverLicense driverLicense, VisaStatus visaStatus, EmergencyContact emergencyContact) {
    }

    public String getId() {
        return id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return email;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public VisaStatus getVisaStatus() {
        return visaStatus;
    }

    public void setVisaStatus(VisaStatus visaStatus) {
        this.visaStatus = visaStatus;
    }

    public EmergencyContact getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(EmergencyContact emergencyContact) {
        this.emergencyContact = emergencyContact;
    }

    public DriverLicense getDriverLicense() {
        return driverLicense;
    }

    public void setDriverLicense(DriverLicense driverLicense) {
        this.driverLicense = driverLicense;
    }

//    public List<String> getPersonalDocuments() {
//        return personalDocuments;
//    }
//
//    public void setPersonalDocuments(List<String> personalDocuments) {
//        this.personalDocuments = personalDocuments;
//    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", preferredName='" + preferredName + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", email='" + email + '\'' +
                ", ssn='" + ssn + '\'' +
                ", dob=" + dob +
                ", gender='" + gender + '\'' +
                ", houseId='" + houseId + '\'' +
                ", address=" + address +
                ", contactInfo=" + contactInfo +
                ", visaStatus=" + visaStatus +
                ", emergencyContact=" + emergencyContact +
                ", driverLicense=" + driverLicense +
//                ", personalDocuments=" + personalDocuments +
                '}';
    }
}
