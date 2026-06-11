package com.noc.service;

import com.noc.dto.ApplicationDTO;
import com.noc.dto.DashboardStatsDTO;
import com.noc.repository.DepartmentRepository;
import com.noc.repository.NocApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private NocApplicationRepository applicationRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public DashboardStatsDTO getDashboardStats() {
        long total = applicationRepository.count();
        long pending = applicationRepository.countByStatus("Submitted") + applicationRepository.countByStatus("Pending");
        long approved = applicationRepository.countByStatus("Approved");
        long rejected = applicationRepository.countByStatus("Rejected");
        long underReview = applicationRepository.countByStatus("Under Review");

        List<ApplicationDTO> recentApps = applicationRepository.findTop10ByOrderByCreatedDateTimeDesc()
                .stream()
                .map(app -> ApplicationDTO.builder()
                        .applicationId(app.getApplicationId())
                        .civilianId(app.getCivilianId())
                        .name(app.getName())
                        .nocSubject(app.getNocSubject())
                        .status(app.getStatus())
                        .createdDateTime(app.getCreatedDateTime())
                        .taluka(app.getTaluka())
                        .village(app.getVillage())
                        .build())
                .collect(Collectors.toList());

        // Simple mock for monthly/department stats as placeholders for dynamic logic
        Map<String, Long> monthlyStats = new HashMap<>();
        monthlyStats.put("January", 5L);
        monthlyStats.put("February", 8L);
        monthlyStats.put("March", 12L);

        Map<String, Long> deptStats = new HashMap<>();
        departmentRepository.findAll().forEach(dept -> {
            long count = applicationRepository.findAll().stream()
                    .filter(app -> app.getNocSubject() != null && app.getNocSubject().contains(dept.getDepartmentName()))
                    .count();
            deptStats.put(dept.getDepartmentName(), count);
        });


        return DashboardStatsDTO.builder()
                .totalApplications(total)
                .pendingApplications(pending)
                .approvedApplications(approved)
                .rejectedApplications(rejected)
                .underReviewApplications(underReview)
                .recentApplications(recentApps)
                .monthlyStatistics(monthlyStats)
                .departmentStatistics(deptStats)
                .build();
    }
}
