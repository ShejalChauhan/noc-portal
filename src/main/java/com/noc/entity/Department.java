package com.noc.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "departmentName")
    private String departmentName;

    private String description;

    @Column(name = "hodName")
    private String hodName;

    @Column(name = "hodNumber", length = 10)
    private String hodNumber;

    @Column(name = "hodPassword", length = 20)
    private String hodPassword;

    private String taluka;

    @Column(nullable = true)
    private String status = "Active";

    @Column(nullable = true)
    private String userId; // user id of creator

    @Column(name = "`createdDateTime`", nullable = true)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    private LocalDateTime updateDateTime;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getHodName() { return hodName; }
    public void setHodName(String hodName) { this.hodName = hodName; }

    public String getHodNumber() { return hodNumber; }
    public void setHodNumber(String hodNumber) { this.hodNumber = hodNumber; }

    public String getHodPassword() { return hodPassword; }
    public void setHodPassword(String hodPassword) { this.hodPassword = hodPassword; }

    public String getTaluka() { return taluka; }
    public void setTaluka(String taluka) { this.taluka = taluka; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public LocalDateTime getCreatedDateTime() { return createdDateTime; }
    public void setCreatedDateTime(LocalDateTime createdDateTime) { this.createdDateTime = createdDateTime; }

    public LocalDateTime getUpdateDateTime() { return updateDateTime; }
    public void setUpdateDateTime(LocalDateTime updateDateTime) { this.updateDateTime = updateDateTime; }
}
