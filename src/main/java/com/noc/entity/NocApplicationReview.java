package com.noc.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "nocApplicationReviews")
public class NocApplicationReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String applicationId;

    @Column(nullable = false)
    private Integer departmentId;

    private String reviewedBy;
    private String forwardEmployee;

    @Column(columnDefinition = "TEXT")
    private String forwardEmployeeDoc;

    @Column(columnDefinition = "TEXT")
    private String employeeReport;

    private String employeeRemark;

    @Column(nullable = false)
    private String status = "Pending"; // Pending, Approved, Rejected, Under Review

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(columnDefinition = "TEXT")
    private String dscDocumentPath;

    private LocalDateTime reviewedDateTime;

    @Column(nullable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getApplicationId() { return applicationId; }
    public void setApplicationId(String applicationId) { this.applicationId = applicationId; }

    public Integer getDepartmentId() { return departmentId; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }

    public String getReviewedBy() { return reviewedBy; }
    public void setReviewedBy(String reviewedBy) { this.reviewedBy = reviewedBy; }

    public String getForwardEmployee() { return forwardEmployee; }
    public void setForwardEmployee(String forwardEmployee) { this.forwardEmployee = forwardEmployee; }

    public String getForwardEmployeeDoc() { return forwardEmployeeDoc; }
    public void setForwardEmployeeDoc(String forwardEmployeeDoc) { this.forwardEmployeeDoc = forwardEmployeeDoc; }

    public String getEmployeeReport() { return employeeReport; }
    public void setEmployeeReport(String employeeReport) { this.employeeReport = employeeReport; }

    public String getEmployeeRemark() { return employeeRemark; }
    public void setEmployeeRemark(String employeeRemark) { this.employeeRemark = employeeRemark; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getDscDocumentPath() { return dscDocumentPath; }
    public void setDscDocumentPath(String dscDocumentPath) { this.dscDocumentPath = dscDocumentPath; }

    public LocalDateTime getReviewedDateTime() { return reviewedDateTime; }
    public void setReviewedDateTime(LocalDateTime reviewedDateTime) { this.reviewedDateTime = reviewedDateTime; }

    public LocalDateTime getCreatedDateTime() { return createdDateTime; }
    public void setCreatedDateTime(LocalDateTime createdDateTime) { this.createdDateTime = createdDateTime; }
}
