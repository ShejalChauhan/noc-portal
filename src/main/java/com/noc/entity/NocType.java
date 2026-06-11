package com.noc.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "nocTypes")
public class NocType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String departmentId; // comma-separated department IDs

    @Column(nullable = false)
    private String finalAuthority;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String status = "Active";

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    private LocalDateTime updatedDateTime;

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String departmentId) { this.departmentId = departmentId; }

    public String getFinalAuthority() { return finalAuthority; }
    public void setFinalAuthority(String finalAuthority) { this.finalAuthority = finalAuthority; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public LocalDateTime getCreatedDateTime() { return createdDateTime; }
    public void setCreatedDateTime(LocalDateTime createdDateTime) { this.createdDateTime = createdDateTime; }

    public LocalDateTime getUpdatedDateTime() { return updatedDateTime; }
    public void setUpdatedDateTime(LocalDateTime updatedDateTime) { this.updatedDateTime = updatedDateTime; }
}
