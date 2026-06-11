package com.noc.repository;

import com.noc.entity.CivilianRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CivilianRegistrationRepository extends JpaRepository<CivilianRegistration, Integer> {
    Optional<CivilianRegistration> findByMobileNo(String mobileNo);
    Optional<CivilianRegistration> findByEmailId(String emailId);
    Optional<CivilianRegistration> findByCivilianId(String civilianId);
    boolean existsByMobileNo(String mobileNo);
    boolean existsByEmailId(String emailId);
    boolean existsByCivilianId(String civilianId);
}
