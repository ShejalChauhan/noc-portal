package com.noc.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    private String applicationId;
    private String civilianId;
    private String name;
    private String nocSubject;
    private String status;
    private LocalDateTime createdDateTime;
    private String taluka;
    private String village;
}
