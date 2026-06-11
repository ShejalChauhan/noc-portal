package com.noc.repository;

import com.noc.entity.Taluka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TalukaRepository extends JpaRepository<Taluka, Integer> {
    List<Taluka> findByStatus(String status);
    
    @Query("SELECT t FROM Taluka t WHERE t.id IN (SELECT MIN(t2.id) FROM Taluka t2 WHERE t2.status = :status GROUP BY t2.taluka)")
    List<Taluka> findDistinctByStatus(@Param("status") String status);

    
    List<Taluka> findByTalukaAndStatus(String taluka, String status);
}
