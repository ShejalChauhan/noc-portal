package com.noc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private long totalApplications;
    private long pendingApplications;
    private long approvedApplications;
    private long rejectedApplications;
    private long underReviewApplications;
    private Map<String, Long> monthlyStatistics;
    private Map<String, Long> departmentStatistics;
    private List<ApplicationDTO> recentApplications;
}
