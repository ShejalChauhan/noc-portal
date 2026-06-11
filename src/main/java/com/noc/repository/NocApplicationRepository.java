package com.noc.repository;

import com.noc.entity.NocApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface NocApplicationRepository extends JpaRepository<NocApplication, Integer> {
    List<NocApplication> findByCivilianId(String civilianId);
    List<NocApplication> findByCivilianIdOrUserId(String civilianId, String userId);
    Optional<NocApplication> findByApplicationId(String applicationId);

    long countByStatus(String status);
    List<NocApplication> findByStatus(String status);
    Page<NocApplication> findByStatus(String status, Pageable pageable);
    
    List<NocApplication> findTop10ByOrderByCreatedDateTimeDesc();
    
    Page<NocApplication> findByCivilianIdOrUserId(String civilianId, String userId, Pageable pageable);
    
    // Additional methods for specific roles or filtering if needed
    List<NocApplication> findByTaluka(String taluka);
    List<NocApplication> findByVillage(String village);


}
