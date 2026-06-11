package com.noc.controller;

import com.noc.dto.ApiResponse;
import com.noc.service.NocReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class NocReviewController {

    @Autowired
    private NocReviewService reviewService;

    @PostMapping("/forward")
    public ResponseEntity<ApiResponse<String>> forward(
            @RequestParam("file1") MultipartFile file1,
            @RequestParam("file2") MultipartFile file2,
            @RequestParam("remark") String remark,
            @RequestParam("applicationId") String applicationId) {

        if (file1.isEmpty() || file2.isEmpty() || remark.isBlank() || applicationId.isBlank()) {
            return ResponseEntity.badRequest().body(ApiResponse.error("All fields (file1, file2, remark, applicationId) are required."));
        }

        try {
            reviewService.forwardApplication(applicationId, file1, file2, remark);
            return ResponseEntity.ok(ApiResponse.success("Application forwarded successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to forward application: " + e.getMessage()));
        }
    }

    @PostMapping("/reject")
    public ResponseEntity<ApiResponse<String>> reject(
            @RequestParam("remark") String remark,
            @RequestParam("applicationId") String applicationId) {

        if (remark.isBlank() || applicationId.isBlank()) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Remark and applicationId are required."));
        }

        try {
            reviewService.rejectApplication(applicationId, remark);
            return ResponseEntity.ok(ApiResponse.success("Application rejected successfully."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to reject application: " + e.getMessage()));
        }
    }
}
