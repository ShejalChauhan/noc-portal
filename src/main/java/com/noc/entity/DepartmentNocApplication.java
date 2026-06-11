package com.noc.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "departmentNocApplications")
public class DepartmentNocApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String applicationId;

    @Column(nullable = false)
    private Integer departmentId;

    @Column(nullable = false)
    private String nocSubject;

    @Column(nullable = false)
    private String landDesc;

    @Column(nullable = false)
    private String taluka;

    @Column(nullable = false)
    private String village;

    @Column(nullable = false)
    private String gatNo;

    @Column(length = 10, nullable = false)
    private String mobileNo;

    private String emailId;

    @Column(nullable = false)
    private Integer nocTypeId;

    private String inspectionOfficer;
    @Column(columnDefinition = "LONGTEXT")
    private String reportFile;

    @Column(name = "noc_file", columnDefinition = "LONGTEXT", nullable = false)
    private String noc_file;

    private String reportRemark;
    private String HODremark;

    @Column(nullable = false)
    private String status = "Submitted"; // Submitted, Under Review, Approved, Rejected, DSC_Uploaded

    @Column(nullable = false)
    private String init_status = "";
    
    @Column(nullable = false)
    private String init_remark = "";
    
    @Column(nullable = false)
    private String final_status = "";
    
    @Column(nullable = false)
    private String final_remark = "";

    @Column(nullable = false)
    private String init_status_thasildar = "";
    
    @Column(nullable = false)
    private String init_remark_tahsildar = "";
    
    @Column(nullable = false)
    private String final_status_tahsildar = "";
    
    @Column(nullable = false)
    private String final_remark_tahsildar = "";

    @Column(nullable = false)
    private Integer fianl_authiroty_remark = 0;

    @Column(nullable = false)
    private Integer final_authrity_file = 0;

    @Column(nullable = false)
    private String userId; // civilian ID

    @Column(nullable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    private LocalDateTime updateDateTime;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getApplicationId() { return applicationId; }
    public void setApplicationId(String applicationId) { this.applicationId = applicationId; }

    public Integer getDepartmentId() { return departmentId; }
    public void setDepartmentId(Integer departmentId) { this.departmentId = departmentId; }

    public String getNocSubject() { return nocSubject; }
    public void setNocSubject(String nocSubject) { this.nocSubject = nocSubject; }

    public String getLandDesc() { return landDesc; }
    public void setLandDesc(String landDesc) { this.landDesc = landDesc; }

    public String getTaluka() { return taluka; }
    public void setTaluka(String taluka) { this.taluka = taluka; }

    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }

    public String getGatNo() { return gatNo; }
    public void setGatNo(String gatNo) { this.gatNo = gatNo; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }

    public Integer getNocTypeId() { return nocTypeId; }
    public void setNocTypeId(Integer nocTypeId) { this.nocTypeId = nocTypeId; }

    public String getInspectionOfficer() { return inspectionOfficer; }
    public void setInspectionOfficer(String inspectionOfficer) { this.inspectionOfficer = inspectionOfficer; }

    public String getReportFile() { return reportFile; }
    public void setReportFile(String reportFile) { this.reportFile = reportFile; }

    public String getNoc_file() { return noc_file; }
    public void setNoc_file(String noc_file) { this.noc_file = noc_file; }

    public String getReportRemark() { return reportRemark; }
    public void setReportRemark(String reportRemark) { this.reportRemark = reportRemark; }

    public String getHODremark() { return HODremark; }
    public void setHODremark(String HODremark) { this.HODremark = HODremark; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getInit_status() { return init_status; }
    public void setInit_status(String init_status) { this.init_status = init_status; }

    public String getInit_remark() { return init_remark; }
    public void setInit_remark(String init_remark) { this.init_remark = init_remark; }

    public String getFinal_status() { return final_status; }
    public void setFinal_status(String final_status) { this.final_status = final_status; }

    public String getFinal_remark() { return final_remark; }
    public void setFinal_remark(String final_remark) { this.final_remark = final_remark; }

    public String getInit_status_thasildar() { return init_status_thasildar; }
    public void setInit_status_thasildar(String init_status_thasildar) { this.init_status_thasildar = init_status_thasildar; }

    public String getInit_remark_tahsildar() { return init_remark_tahsildar; }
    public void setInit_remark_tahsildar(String init_remark_tahsildar) { this.init_remark_tahsildar = init_remark_tahsildar; }

    public String getFinal_status_tahsildar() { return final_status_tahsildar; }
    public void setFinal_status_tahsildar(String final_status_tahsildar) { this.final_status_tahsildar = final_status_tahsildar; }

    public String getFinal_remark_tahsildar() { return final_remark_tahsildar; }
    public void setFinal_remark_tahsildar(String final_remark_tahsildar) { this.final_remark_tahsildar = final_remark_tahsildar; }

    public Integer getFianl_authiroty_remark() { return fianl_authiroty_remark; }
    public void setFianl_authiroty_remark(Integer fianl_authiroty_remark) { this.fianl_authiroty_remark = fianl_authiroty_remark; }

    public Integer getFinal_authrity_file() { return final_authrity_file; }
    public void setFinal_authrity_file(Integer final_authrity_file) { this.final_authrity_file = final_authrity_file; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public LocalDateTime getCreatedDateTime() { return createdDateTime; }
    public void setCreatedDateTime(LocalDateTime createdDateTime) { this.createdDateTime = createdDateTime; }

    public LocalDateTime getUpdateDateTime() { return updateDateTime; }
    public void setUpdateDateTime(LocalDateTime updateDateTime) { this.updateDateTime = updateDateTime; }
}
