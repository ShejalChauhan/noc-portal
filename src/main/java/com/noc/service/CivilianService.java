package com.noc.service;

import com.noc.entity.CivilianRegistration;
import com.noc.entity.DepartmentNocApplication;
import com.noc.repository.DepartmentNocApplicationRepository;
import com.noc.repository.CivilianRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CivilianService {

    @Autowired
    private DepartmentNocApplicationRepository applicationRepository;

    @Autowired
    private CivilianRegistrationRepository civilianRegistrationRepository;

    public CivilianRegistration registerCivilian(CivilianRegistration civilian) {
        System.out.println("[CivilianService] Attempting registration for: " + civilian.getName());
        
        // Auto-generate Civilian ID if missing
        if (civilian.getCivilianId() == null || civilian.getCivilianId().isBlank()) {
            long count = civilianRegistrationRepository.count();
            String generatedId = "CIV-2026-" + String.format("%03d", count + 1);
            civilian.setCivilianId(generatedId);
            System.out.println("[CivilianService] Generated New ID: " + generatedId);
        }

        if (civilian.getIdentificationCertificate() != null) {
            System.out.println("[CivilianService] ID Certificate Length: " + civilian.getIdentificationCertificate().length() + " characters.");
        }

        if (civilianRegistrationRepository.existsByMobileNo(civilian.getMobileNo())) {
            throw new IllegalArgumentException("Mobile number is already registered.");
        }
        if (civilianRegistrationRepository.existsByEmailId(civilian.getEmailId())) {
            throw new IllegalArgumentException("Email ID is already registered.");
        }
        if (civilian.getCivilianId() != null && !civilian.getCivilianId().isBlank() && civilianRegistrationRepository.existsByCivilianId(civilian.getCivilianId())) {
            throw new IllegalArgumentException("Civilian ID already exists.");
        }
        return civilianRegistrationRepository.save(civilian);
    }

    public List<DepartmentNocApplication> getApplicationsByUserId(String userId) {
        return applicationRepository.findByUserId(userId);
    }

    public DepartmentNocApplication submitApplication(DepartmentNocApplication application) {
        // Calculate the next sequential Application ID in the NOC-2026-XXX format
        long totalRecords = applicationRepository.count();
        String formattedSeq = String.format("%03d", totalRecords + 1);
        String generatedId = "NOC-2026-" + formattedSeq;
        
        application.setApplicationId(generatedId);
        System.out.println("[CivilianService] Submitting new NOC Application: " + generatedId);
        
        return applicationRepository.save(application);
    }

    public Optional<DepartmentNocApplication> getApplicationById(String applicationId) {
        return applicationRepository.findByApplicationId(applicationId);
    }

    public List<DepartmentNocApplication> getAllApplications() {
        return applicationRepository.findAll();
    }

    public Optional<DepartmentNocApplication> updateApplicationStatus(String applicationId, DepartmentNocApplication statusDetails) {
        return applicationRepository.findByApplicationId(applicationId).map(app -> {
            if (statusDetails.getStatus() != null) {
                app.setStatus(statusDetails.getStatus());
            }
            if (statusDetails.getInspectionOfficer() != null) {
                app.setInspectionOfficer(statusDetails.getInspectionOfficer());
            }
            if (statusDetails.getHODremark() != null) {
                app.setHODremark(statusDetails.getHODremark());
            }
            if (statusDetails.getReportRemark() != null) {
                app.setReportRemark(statusDetails.getReportRemark());
            }
            if (statusDetails.getInit_status() != null) {
                app.setInit_status(statusDetails.getInit_status());
            }
            if (statusDetails.getInit_remark() != null) {
                app.setInit_remark(statusDetails.getInit_remark());
            }
            if (statusDetails.getFinal_status() != null) {
                app.setFinal_status(statusDetails.getFinal_status());
            }
            if (statusDetails.getFinal_remark() != null) {
                app.setFinal_remark(statusDetails.getFinal_remark());
            }
            app.setUpdateDateTime(java.time.LocalDateTime.now());
            return applicationRepository.save(app);
        });
    }

    public Optional<CivilianRegistration> getCivilianProfile(String mobileNo) {
        return civilianRegistrationRepository.findByMobileNo(mobileNo);
    }
}
