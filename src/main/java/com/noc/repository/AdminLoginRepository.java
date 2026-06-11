package com.noc.repository;

import com.noc.entity.AdminLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AdminLoginRepository extends JpaRepository<AdminLogin, Integer> {
    Optional<AdminLogin> findByUsername(String username);
    Optional<AdminLogin> findByUserId(String userId);
}
