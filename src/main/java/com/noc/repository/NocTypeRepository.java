package com.noc.repository;

import com.noc.entity.NocType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NocTypeRepository extends JpaRepository<NocType, Integer> {
    List<NocType> findByStatus(String status);
}
