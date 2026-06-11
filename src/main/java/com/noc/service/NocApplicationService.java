package com.noc.service;

import com.noc.entity.NocApplication;
import com.noc.repository.NocApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class NocApplicationService {

    @Autowired
    private NocApplicationRepository repository;

    public List<NocApplication> getApplicationsByCivilianId(String civilianId) {
        return repository.findByCivilianIdOrUserId(civilianId, civilianId);
    }

    public org.springframework.data.domain.Page<NocApplication> getApplicationsByCivilianId(String civilianId, org.springframework.data.domain.Pageable pageable) {
        return repository.findByCivilianIdOrUserId(civilianId, civilianId, pageable);
    }


    public NocApplication submitApplication(NocApplication application) {
        long totalRecords = repository.count();
        String formattedSeq = String.format("%03d", totalRecords + 1);
        application.setApplicationId("NOC-2026-" + formattedSeq);
        application.setCreatedDateTime(LocalDateTime.now());
        if ((application.getCivilianId() == null || application.getCivilianId().isBlank())
                && application.getUserId() != null && !application.getUserId().isBlank()) {
            application.setCivilianId(application.getUserId());
        }
        if ((application.getUserId() == null || application.getUserId().isBlank())
                && application.getCivilianId() != null && !application.getCivilianId().isBlank()) {
            application.setUserId(application.getCivilianId());
        }
        if (application.getStatus() == null || application.getStatus().isEmpty()) {
            application.setStatus("Submitted");
        }
        return repository.save(application);
    }

    public Optional<NocApplication> getApplicationById(String applicationId) {
        return repository.findByApplicationId(applicationId);
    }

    public List<NocApplication> getApplicationsByStatus(String status) {
        return repository.findByStatus(status);
    }

    public org.springframework.data.domain.Page<NocApplication> getApplicationsByStatus(String status, org.springframework.data.domain.Pageable pageable) {
        return repository.findByStatus(status, pageable);
    }


    public List<NocApplication> getPendingApplications() {
        // Applications that are not Approved or Rejected are considered Pending/Under Review
        return repository.findAll().stream()
                .filter(app -> !"Approved".equalsIgnoreCase(app.getStatus()) && !"Rejected".equalsIgnoreCase(app.getStatus()))
                .collect(Collectors.toList());
    }

    public List<NocApplication> getApprovedApplications() {
        return repository.findByStatus("Approved");
    }

    public List<NocApplication> getRejectedApplications() {
        return repository.findByStatus("Rejected");
    }


    public Optional<NocApplication> updateApplication(String applicationId, NocApplication updatedData) {
        return repository.findByApplicationId(applicationId).map(app -> {
            if (updatedData.getStatus() != null) app.setStatus(updatedData.getStatus());
            if (updatedData.getInspectionOfficer() != null) app.setInspectionOfficer(updatedData.getInspectionOfficer());
            if (updatedData.getToInspectionOfficer() != null) app.setToInspectionOfficer(updatedData.getToInspectionOfficer());
            if (updatedData.getReportFile() != null) app.setReportFile(updatedData.getReportFile());
            if (updatedData.getReportRemark() != null) app.setReportRemark(updatedData.getReportRemark());
            if (updatedData.getHODremark() != null) app.setHODremark(updatedData.getHODremark());
            
            // Final authority status & remarks
            if (updatedData.getInit_status() != null) app.setInit_status(updatedData.getInit_status());
            if (updatedData.getInitReportFile() != null) app.setInitReportFile(updatedData.getInitReportFile());
            if (updatedData.getInit_remark() != null) app.setInit_remark(updatedData.getInit_remark());
            if (updatedData.getFinal_status() != null) app.setFinal_status(updatedData.getFinal_status());
            if (updatedData.getFinal_remark() != null) app.setFinal_remark(updatedData.getFinal_remark());
            
            // Tahsildar status & remarks
            if (updatedData.getInit_status_tahsildar() != null) app.setInit_status_tahsildar(updatedData.getInit_status_tahsildar());
            if (updatedData.getInit_remark_tahsildar() != null) app.setInit_remark_tahsildar(updatedData.getInit_remark_tahsildar());
            if (updatedData.getFinal_status_tahsildar() != null) app.setFinal_status_tahsildar(updatedData.getFinal_status_tahsildar());
            if (updatedData.getFinal_remark_tahsildar() != null) app.setFinal_remark_tahsildar(updatedData.getFinal_remark_tahsildar());
            if (updatedData.getFinal_dsc_document() != null) app.setFinal_dsc_document(updatedData.getFinal_dsc_document());

            // Tahildar specific workflow status fields
            if (updatedData.getTahildarStatus() != null) app.setTahildarStatus(updatedData.getTahildarStatus());
            if (updatedData.getTahildarRemark() != null) app.setTahildarRemark(updatedData.getTahildarRemark());
            if (updatedData.getTahildarFile() != null) app.setTahildarFile(updatedData.getTahildarFile());
            if (updatedData.getFinalTahildarStatus() != null) app.setFinalTahildarStatus(updatedData.getFinalTahildarStatus());
            if (updatedData.getFinalTahildarRemark() != null) app.setFinalTahildarRemark(updatedData.getFinalTahildarRemark());
            if (updatedData.getFinalTahildarFile() != null) app.setFinalTahildarFile(updatedData.getFinalTahildarFile());

            // Department workflow status fields
            if (updatedData.getDepartmentStatus() != null) app.setDepartmentStatus(updatedData.getDepartmentStatus());
            if (updatedData.getDepartmentRemark() != null) app.setDepartmentRemark(updatedData.getDepartmentRemark());
            if (updatedData.getDepartmentFile() != null) app.setDepartmentFile(updatedData.getDepartmentFile());

            // SDO workflow status fields
            if (updatedData.getSdoFinalStatus() != null) app.setSdoFinalStatus(updatedData.getSdoFinalStatus());
            if (updatedData.getSdoFinalRemark() != null) app.setSdoFinalRemark(updatedData.getSdoFinalRemark());
            if (updatedData.getSdoFinalDocument() != null) app.setSdoFinalDocument(updatedData.getSdoFinalDocument());

            // Final Authority workflow status fields
            if (updatedData.getFinalAuthStatus() != null) app.setFinalAuthStatus(updatedData.getFinalAuthStatus());
            if (updatedData.getFinalAuthRemark() != null) app.setFinalAuthRemark(updatedData.getFinalAuthRemark());
            if (updatedData.getFinalAuthFile() != null) app.setFinalAuthFile(updatedData.getFinalAuthFile());

            app.setUpdateDateTime(LocalDateTime.now());
            return repository.save(app);
        });
    }
}
