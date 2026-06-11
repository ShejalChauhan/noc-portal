package com.noc.repository;

import com.noc.entity.DepartmentNocApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentNocApplicationRepository extends JpaRepository<DepartmentNocApplication, Integer> {
    List<DepartmentNocApplication> findByUserId(String userId);
    Optional<DepartmentNocApplication> findByApplicationId(String applicationId);
    List<DepartmentNocApplication> findByStatus(String status);
}
