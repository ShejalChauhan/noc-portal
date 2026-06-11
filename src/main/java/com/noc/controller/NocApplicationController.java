package com.noc.controller;

import com.noc.dto.ApiResponse;
import com.noc.entity.NocApplication;
import com.noc.service.NocApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class NocApplicationController {

    @Autowired
    private NocApplicationService service;

    @GetMapping("/applications")
    public ResponseEntity<ApiResponse<Page<NocApplication>>> getApplications(
            @RequestParam(required = false) String civilianId,
            @RequestParam(required = false) String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDateTime") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {
        
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<NocApplication> apps;
        if (civilianId != null && !civilianId.isEmpty() && !"null".equals(civilianId)) {
            apps = service.getApplicationsByCivilianId(civilianId, pageable);
        } else if (userId != null && !userId.isEmpty() && !"null".equals(userId)) {
            apps = service.getApplicationsByCivilianId(userId, pageable);
        } else {
            apps = service.getApplicationsByStatus("Submitted", pageable); 
        }
        return ResponseEntity.ok(ApiResponse.success(apps));
    }


    @GetMapping("/applications/pending")
    public ResponseEntity<ApiResponse<List<NocApplication>>> getPending() {
        return ResponseEntity.ok(ApiResponse.success(service.getPendingApplications()));
    }

    @GetMapping("/applications/approved")
    public ResponseEntity<ApiResponse<List<NocApplication>>> getApproved() {
        return ResponseEntity.ok(ApiResponse.success(service.getApprovedApplications()));
    }

    @GetMapping("/applications/rejected")
    public ResponseEntity<ApiResponse<List<NocApplication>>> getRejected() {
        return ResponseEntity.ok(ApiResponse.success(service.getRejectedApplications()));
    }

    @GetMapping("/applications/{id}")
    public ResponseEntity<ApiResponse<NocApplication>> getApplicationById(@PathVariable String id) {
        return service.getApplicationById(id)
                .map(app -> ResponseEntity.ok(ApiResponse.success(app)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Civilian Specific APIs
    @PostMapping("/application/create")
    public ResponseEntity<ApiResponse<NocApplication>> createApplication(@RequestBody NocApplication application) {
        if ((application.getCivilianId() == null || application.getCivilianId().isEmpty())
                && (application.getUserId() == null || application.getUserId().isEmpty())) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Civilian ID is required."));
        }
        NocApplication submitted = service.submitApplication(application);
        return ResponseEntity.ok(ApiResponse.success(submitted));
    }

    @GetMapping("/application/status")
    public ResponseEntity<ApiResponse<String>> getStatus(@RequestParam String applicationId) {
        return service.getApplicationById(applicationId)
                .map(app -> ResponseEntity.ok(ApiResponse.success(app.getStatus())))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/application/history")
    public ResponseEntity<ApiResponse<List<NocApplication>>> getHistory(@RequestParam String civilianId) {
        return ResponseEntity.ok(ApiResponse.success(service.getApplicationsByCivilianId(civilianId)));
    }

    // Role Specific Update APIs
    @PostMapping("/employee/remark")
    public ResponseEntity<ApiResponse<NocApplication>> employeeRemark(@RequestBody NocApplication data) {
        return service.updateApplication(data.getApplicationId(), data)
                .map(app -> ResponseEntity.ok(ApiResponse.success(app)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/department/approve")
    public ResponseEntity<ApiResponse<NocApplication>> departmentApprove(@RequestBody NocApplication data) {
        data.setStatus("Department Approved");
        return service.updateApplication(data.getApplicationId(), data)
                .map(app -> ResponseEntity.ok(ApiResponse.success(app)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/final-authority/approve")
    public ResponseEntity<ApiResponse<NocApplication>> finalApprove(@RequestBody NocApplication data) {
        data.setStatus("Approved");
        return service.updateApplication(data.getApplicationId(), data)
                .map(app -> ResponseEntity.ok(ApiResponse.success(app)))
                .orElse(ResponseEntity.notFound().build());
    }
}

