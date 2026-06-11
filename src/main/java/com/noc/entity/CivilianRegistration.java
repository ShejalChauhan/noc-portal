package com.noc.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "civilianRegistrations")
public class CivilianRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String civilianId;

    @Column(nullable = false)
    private String name;

    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String address;

    @Column(length = 6, nullable = false)
    private String pinCode;

    @Column(nullable = false)
    private String taluka;

    @Column(nullable = false)
    private String village;

    @Column(name = "aadharNo", length = 12, nullable = false)
    private String aadharNo;

    @Column(name = "emailId", nullable = false)
    private String emailId;

    private LocalDate dob;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String identificationCertificate;

    @Column(name = "mobileNo", length = 10, nullable = false)
    private String mobileNo;

    @Column(nullable = false)
    private String password;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "LONGTEXT")
    private String profilePic;

    @Column(nullable = false)
    private String status = "Active";

    @Column(nullable = false)
    private LocalDateTime createDateTime = LocalDateTime.now();

    private LocalDateTime updatedDateTime;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCivilianId() { return civilianId; }
    public void setCivilianId(String civilianId) { this.civilianId = civilianId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPinCode() { return pinCode; }
    public void setPinCode(String pinCode) { this.pinCode = pinCode; }

    public String getTaluka() { return taluka; }
    public void setTaluka(String taluka) { this.taluka = taluka; }

    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }

    public String getAadharNo() { return aadharNo; }
    public void setAadharNo(String aadharNo) { this.aadharNo = aadharNo; }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public String getIdentificationCertificate() { return identificationCertificate; }
    public void setIdentificationCertificate(String identificationCertificate) { this.identificationCertificate = identificationCertificate; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getProfilePic() { return profilePic; }
    public void setProfilePic(String profilePic) { this.profilePic = profilePic; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreateDateTime() { return createDateTime; }
    public void setCreateDateTime(LocalDateTime createDateTime) { this.createDateTime = createDateTime; }

    public LocalDateTime getUpdatedDateTime() { return updatedDateTime; }
    public void setUpdatedDateTime(LocalDateTime updatedDateTime) { this.updatedDateTime = updatedDateTime; }
}
