package com.noc.repository;

import com.noc.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Optional<Department> findByHodNumber(String hodNumber);
    Optional<Department> findByUserId(String userId);
    Optional<Department> findByDepartmentName(String departmentName);
    List<Department> findByStatus(String status);
}
