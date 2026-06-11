package com.noc.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.time.LocalDateTime;

@Entity
@Table(name = "nocApplications")
public class NocApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String applicationId;

    @Column(nullable = false)
    private String civilianId;

    @Column(nullable = false)
    private String nocSubject;

    @Column(nullable = false)
    private Integer nocTypeId;

    @Column(nullable = false)
    private String name;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String address;

    @Column(nullable = false)
    private String emailId;

    @Column(length = 10, nullable = false)
    private String mobileNo;

    @Column(length = 12, nullable = false)
    private String aadharNo;

    private String landDesc;
    private String taluka;
    private String village;
    private String gatNo;
    private String landType;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "LONGTEXT")
    private String panCard;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "LONGTEXT")
    private String aadharCard;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String nocApplicationFile;

    private String inspectionOfficer;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "LONGTEXT")
    private String toInspectionOfficer;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "LONGTEXT")
    private String reportFile;
    
    private String reportRemark;
    private String HODremark;

    @Column(nullable = false)
    private String status = "Submitted";

    // Final Authority fields
    private String init_status;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "LONGTEXT")
    private String initReportFile;

    private String init_remark;
    private String final_status;
    private String final_remark;

    // Tahsildar fields
    private String init_status_tahsildar;
    private String init_remark_tahsildar;
    private String final_status_tahsildar;
    private String final_remark_tahsildar;

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "LONGTEXT")
    private String final_dsc_document;

    // Workflow pipeline fields
    @Column(nullable = false)
    private String tahildarStatus = "";

    @Column(nullable = false)
    private String tahildarRemark = "";

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String tahildarFile = "";

    @Column(nullable = false)
    private String finalTahildarStatus = "";

    @Column(nullable = false)
    private String finalTahildarRemark = "";

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String finalTahildarFile = "";

    @Column(nullable = false)
    private String departmentStatus = "";

    @Column(nullable = false)
    private String departmentRemark = "";

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(columnDefinition = "LONGTEXT", nullable = false)
    private String departmentFile = "";

    @Column(name = "SDO_final_status", nullable = false)
    private String sdoFinalStatus = "";

    @Column(name = "SDO_final_remark", nullable = false)
    private String sdoFinalRemark = "";

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(name = "SDO_final_document", columnDefinition = "LONGTEXT", nullable = false)
    private String sdoFinalDocument = "";

    @Column(name = "final_Auth_status", nullable = false)
    private String finalAuthStatus = "";

    @Column(name = "final_Auth_remark", nullable = false)
    private String finalAuthRemark = "";

    @Lob
    @JdbcTypeCode(SqlTypes.LONGVARCHAR)
    @Column(name = "final_Auth_file", columnDefinition = "LONGTEXT", nullable = false)
    private String finalAuthFile = "";

    @Column(nullable = false)
    private String currentRole = "CIVILIAN";

    @Column(nullable = false)
    private String currentStatus = "SUBMITTED";

    @Column(nullable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    private LocalDateTime updateDateTime;

    private String userId;

    // Getters and Setters
    public String getCurrentRole() { return currentRole; }
    public void setCurrentRole(String currentRole) { this.currentRole = currentRole; }

    public String getCurrentStatus() { return currentStatus; }
    public void setCurrentStatus(String currentStatus) { this.currentStatus = currentStatus; }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getApplicationId() { return applicationId; }
    public void setApplicationId(String applicationId) { this.applicationId = applicationId; }

    public String getCivilianId() { return civilianId; }
    public void setCivilianId(String civilianId) { this.civilianId = civilianId; }

    public String getNocSubject() { return nocSubject; }
    public void setNocSubject(String nocSubject) { this.nocSubject = nocSubject; }

    public Integer getNocTypeId() { return nocTypeId; }
    public void setNocTypeId(Integer nocTypeId) { this.nocTypeId = nocTypeId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmailId() { return emailId; }
    public void setEmailId(String emailId) { this.emailId = emailId; }

    public String getMobileNo() { return mobileNo; }
    public void setMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

    public String getAadharNo() { return aadharNo; }
    public void setAadharNo(String aadharNo) { this.aadharNo = aadharNo; }

    public String getLandDesc() { return landDesc; }
    public void setLandDesc(String landDesc) { this.landDesc = landDesc; }

    public String getTaluka() { return taluka; }
    public void setTaluka(String taluka) { this.taluka = taluka; }

    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }

    public String getGatNo() { return gatNo; }
    public void setGatNo(String gatNo) { this.gatNo = gatNo; }

    public String getLandType() { return landType; }
    public void setLandType(String landType) { this.landType = landType; }

    public String getPanCard() { return panCard; }
    public void setPanCard(String panCard) { this.panCard = panCard; }

    public String getAadharCard() { return aadharCard; }
    public void setAadharCard(String aadharCard) { this.aadharCard = aadharCard; }

    public String getNocApplicationFile() { return nocApplicationFile; }
    public void setNocApplicationFile(String nocApplicationFile) { this.nocApplicationFile = nocApplicationFile; }

    public String getInspectionOfficer() { return inspectionOfficer; }
    public void setInspectionOfficer(String inspectionOfficer) { this.inspectionOfficer = inspectionOfficer; }

    public String getToInspectionOfficer() { return toInspectionOfficer; }
    public void setToInspectionOfficer(String toInspectionOfficer) { this.toInspectionOfficer = toInspectionOfficer; }

    public String getReportFile() { return reportFile; }
    public void setReportFile(String reportFile) { this.reportFile = reportFile; }

    public String getReportRemark() { return reportRemark; }
    public void setReportRemark(String reportRemark) { this.reportRemark = reportRemark; }

    public String getHODremark() { return HODremark; }
    public void setHODremark(String HODremark) { this.HODremark = HODremark; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getInit_status() { return init_status; }
    public void setInit_status(String init_status) { this.init_status = init_status; }

    public String getInitReportFile() { return initReportFile; }
    public void setInitReportFile(String initReportFile) { this.initReportFile = initReportFile; }

    public String getInit_remark() { return init_remark; }
    public void setInit_remark(String init_remark) { this.init_remark = init_remark; }

    public String getFinal_status() { return final_status; }
    public void setFinal_status(String final_status) { this.final_status = final_status; }

    public String getFinal_remark() { return final_remark; }
    public void setFinal_remark(String final_remark) { this.final_remark = final_remark; }

    public String getInit_status_tahsildar() { return init_status_tahsildar; }
    public void setInit_status_tahsildar(String init_status_tahsildar) { this.init_status_tahsildar = init_status_tahsildar; }

    public String getInit_remark_tahsildar() { return init_remark_tahsildar; }
    public void setInit_remark_tahsildar(String init_remark_tahsildar) { this.init_remark_tahsildar = init_remark_tahsildar; }

    public String getFinal_status_tahsildar() { return final_status_tahsildar; }
    public void setFinal_status_tahsildar(String final_status_tahsildar) { this.final_status_tahsildar = final_status_tahsildar; }

    public String getFinal_remark_tahsildar() { return final_remark_tahsildar; }
    public void setFinal_remark_tahsildar(String final_remark_tahsildar) { this.final_remark_tahsildar = final_remark_tahsildar; }

    public String getFinal_dsc_document() { return final_dsc_document; }
    public void setFinal_dsc_document(String final_dsc_document) { this.final_dsc_document = final_dsc_document; }

    public String getTahildarStatus() { return tahildarStatus; }
    public void setTahildarStatus(String tahildarStatus) { this.tahildarStatus = tahildarStatus; }

    public String getTahildarRemark() { return tahildarRemark; }
    public void setTahildarRemark(String tahildarRemark) { this.tahildarRemark = tahildarRemark; }

    public String getTahildarFile() { return tahildarFile; }
    public void setTahildarFile(String tahildarFile) { this.tahildarFile = tahildarFile; }

    public String getFinalTahildarStatus() { return finalTahildarStatus; }
    public void setFinalTahildarStatus(String finalTahildarStatus) { this.finalTahildarStatus = finalTahildarStatus; }

    public String getFinalTahildarRemark() { return finalTahildarRemark; }
    public void setFinalTahildarRemark(String finalTahildarRemark) { this.finalTahildarRemark = finalTahildarRemark; }

    public String getFinalTahildarFile() { return finalTahildarFile; }
    public void setFinalTahildarFile(String finalTahildarFile) { this.finalTahildarFile = finalTahildarFile; }

    public String getDepartmentStatus() { return departmentStatus; }
    public void setDepartmentStatus(String departmentStatus) { this.departmentStatus = departmentStatus; }

    public String getDepartmentRemark() { return departmentRemark; }
    public void setDepartmentRemark(String departmentRemark) { this.departmentRemark = departmentRemark; }

    public String getDepartmentFile() { return departmentFile; }
    public void setDepartmentFile(String departmentFile) { this.departmentFile = departmentFile; }

    public String getSdoFinalStatus() { return sdoFinalStatus; }
    public void setSdoFinalStatus(String sdoFinalStatus) { this.sdoFinalStatus = sdoFinalStatus; }

    public String getSdoFinalRemark() { return sdoFinalRemark; }
    public void setSdoFinalRemark(String sdoFinalRemark) { this.sdoFinalRemark = sdoFinalRemark; }

    public String getSdoFinalDocument() { return sdoFinalDocument; }
    public void setSdoFinalDocument(String sdoFinalDocument) { this.sdoFinalDocument = sdoFinalDocument; }

    public String getFinalAuthStatus() { return finalAuthStatus; }
    public void setFinalAuthStatus(String finalAuthStatus) { this.finalAuthStatus = finalAuthStatus; }

    public String getFinalAuthRemark() { return finalAuthRemark; }
    public void setFinalAuthRemark(String finalAuthRemark) { this.finalAuthRemark = finalAuthRemark; }

    public String getFinalAuthFile() { return finalAuthFile; }
    public void setFinalAuthFile(String finalAuthFile) { this.finalAuthFile = finalAuthFile; }

    public LocalDateTime getCreatedDateTime() { return createdDateTime; }
    public void setCreatedDateTime(LocalDateTime createdDateTime) { this.createdDateTime = createdDateTime; }

    public LocalDateTime getUpdateDateTime() { return updateDateTime; }
    public void setUpdateDateTime(LocalDateTime updateDateTime) { this.updateDateTime = updateDateTime; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
}
