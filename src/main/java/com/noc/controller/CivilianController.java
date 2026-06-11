package com.noc.controller;

import com.noc.entity.DepartmentNocApplication;
import com.noc.entity.CivilianRegistration;
import com.noc.service.CivilianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/civilian")
@CrossOrigin(origins = "http://localhost:5173") // Allow React Dev Server CORS requests
public class CivilianController {

    @Autowired
    private CivilianService civilianService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody CivilianRegistration civilian) {
        if (civilian.getMobileNo() == null || civilian.getMobileNo().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Mobile number is required."));
        }
        // Auto-generate civilianId if empty (CIV-XXXXXX)
        if (civilian.getCivilianId() == null || civilian.getCivilianId().isEmpty()) {
            civilian.setCivilianId("CIV-" + (System.currentTimeMillis() % 1000000));
        }
        try {
            CivilianRegistration saved = civilianService.registerCivilian(civilian);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(409).body(Map.of("message", ex.getMessage()));
        }
    }

    @GetMapping("/applications")
    public ResponseEntity<List<DepartmentNocApplication>> getApplications(@RequestParam(required = false) String userId) {
        if (userId == null || userId.isEmpty() || "null".equals(userId)) {
            List<DepartmentNocApplication> allApps = civilianService.getAllApplications();
            return ResponseEntity.ok(allApps);
        }
        List<DepartmentNocApplication> apps = civilianService.getApplicationsByUserId(userId);
        return ResponseEntity.ok(apps);
    }

    @PostMapping("/applications/{applicationId}/status")
    public ResponseEntity<DepartmentNocApplication> updateStatus(
            @PathVariable String applicationId,
            @RequestBody DepartmentNocApplication statusDetails) {
        return civilianService.updateApplicationStatus(applicationId, statusDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/profile")
    public ResponseEntity<CivilianRegistration> getProfile(@RequestParam String mobileNo) {
        return civilianService.getCivilianProfile(mobileNo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/submit")
    public ResponseEntity<DepartmentNocApplication> submit(@RequestBody DepartmentNocApplication application) {
        if (application.getUserId() == null || application.getUserId().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        DepartmentNocApplication submitted = civilianService.submitApplication(application);
        return ResponseEntity.ok(submitted);
    }

    @GetMapping("/track/{applicationId}")
    public ResponseEntity<DepartmentNocApplication> getApplication(@PathVariable String applicationId) {
        return civilianService.getApplicationById(applicationId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
