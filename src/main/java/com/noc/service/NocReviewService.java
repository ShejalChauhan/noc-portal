package com.noc.service;

import com.noc.entity.NocApplication;
import com.noc.entity.NocApplicationReview;
import com.noc.repository.NocApplicationRepository;
import com.noc.repository.NocApplicationReviewRepository;
import com.noc.repository.NocTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class NocReviewService {

    @Autowired
    private NocApplicationReviewRepository reviewRepository;

    @Autowired
    private NocApplicationRepository applicationRepository;

    @Autowired
    private NocTypeRepository nocTypeRepository;

    private final String uploadDir = "uploads";

    @Transactional
    public void forwardApplication(String applicationId, MultipartFile file1, MultipartFile file2, String remark) throws IOException {
        NocApplication app = applicationRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found: " + applicationId));

        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save files
        String file1Name = saveFile(file1);
        String file2Name = saveFile(file2);

        // Store metadata in NocApplicationReview
        NocApplicationReview review = new NocApplicationReview();
        review.setApplicationId(applicationId);
        review.setRemarks(remark);
        review.setForwardEmployeeDoc(file1Name);
        review.setDscDocumentPath(file2Name);
        review.setStatus("Forwarded");
        review.setCreatedDateTime(LocalDateTime.now());
        review.setReviewedDateTime(LocalDateTime.now());
        
        // Get departmentId from NocType
        Integer deptId = getDepartmentIdFromApp(app);
        review.setDepartmentId(deptId);

        app.setStatus("Forwarded");
        app.setUpdateDateTime(LocalDateTime.now());
        
        applicationRepository.save(app);
        reviewRepository.save(review);
    }

    @Transactional
    public void rejectApplication(String applicationId, String remark) {
        NocApplication app = applicationRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new IllegalArgumentException("Application not found: " + applicationId));

        // Store metadata in NocApplicationReview
        NocApplicationReview review = new NocApplicationReview();
        review.setApplicationId(applicationId);
        review.setRemarks(remark);
        review.setStatus("Rejected");
        review.setCreatedDateTime(LocalDateTime.now());
        review.setReviewedDateTime(LocalDateTime.now());

        Integer deptId = getDepartmentIdFromApp(app);
        review.setDepartmentId(deptId);

        app.setStatus("Rejected");
        app.setUpdateDateTime(LocalDateTime.now());
        
        applicationRepository.save(app);
        reviewRepository.save(review);
    }

    private Integer getDepartmentIdFromApp(NocApplication app) {
        if (app.getNocTypeId() != null) {
            return nocTypeRepository.findById(app.getNocTypeId())
                    .map(type -> {
                        try {
                            String deptIdStr = type.getDepartmentId().split(",")[0];
                            return Integer.parseInt(deptIdStr.trim());
                        } catch (Exception e) {
                            return 1;
                        }
                    }).orElse(1);
        }
        return 1;
    }

    private String saveFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }
        
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
        
        Path uploadPath = Paths.get(uploadDir);
        Path filePath = uploadPath.resolve(fileName);
        
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
}
