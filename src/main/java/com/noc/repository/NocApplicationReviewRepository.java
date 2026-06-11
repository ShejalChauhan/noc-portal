package com.noc.repository;

import com.noc.entity.NocApplicationReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NocApplicationReviewRepository extends JpaRepository<NocApplicationReview, Integer> {
    List<NocApplicationReview> findByApplicationId(String applicationId);
    List<NocApplicationReview> findByDepartmentIdAndStatus(Integer departmentId, String status);
}
