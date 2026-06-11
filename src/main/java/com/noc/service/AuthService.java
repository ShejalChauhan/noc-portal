package com.noc.service;

import com.noc.entity.AdminLogin;
import com.noc.entity.CivilianRegistration;
import com.noc.entity.Department;
import com.noc.repository.AdminLoginRepository;
import com.noc.repository.CivilianRegistrationRepository;
import com.noc.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private CivilianRegistrationRepository civilianRepository;

    @Autowired
    private AdminLoginRepository adminRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public Map<String, Object> authenticate(String roleId, String username, String password) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);

        if (username == null || password == null) {
            response.put("message", "Credentials cannot be null.");
            return response;
        }

        String inputUser = username.trim();
        String inputPass = password.trim();

        System.out.println("[Auth] Attempting login - Role: " + roleId + ", User: " + inputUser);

        if ("civilian".equals(roleId)) {
            // Civilian Authentication via mobile number OR email
            Optional<CivilianRegistration> civilian = civilianRepository.findByMobileNo(inputUser);
            if (civilian.isEmpty()) {
                civilian = civilianRepository.findByEmailId(inputUser);
            }
            
            if (civilian.isPresent()) {
                System.out.println("[Auth] Civilian found: " + civilian.get().getName() + ", DB Pass: [" + civilian.get().getPassword() + "], Input Pass: [" + inputPass + "]");
                if (civilian.get().getPassword().trim().equals(inputPass)) {
                    response.put("success", true);
                    response.put("role", "civilian");
                    response.put("username", civilian.get().getMobileNo());
                    response.put("displayName", civilian.get().getName());
                    response.put("userId", civilian.get().getCivilianId());
                    // Preserve other fields for existing frontend if needed
                    response.put("name", civilian.get().getName());
                    response.put("status", civilian.get().getStatus());
                    response.put("email", civilian.get().getEmailId());
                    response.put("mobile", civilian.get().getMobileNo());

                    System.out.println("[Auth] Civilian login successful: " + inputUser);
                } else {
                    response.put("message", "अवैध पासवर्ड. (Invalid Password.)");
                    System.out.println("[Auth] Civilian login failed: Invalid Password for " + inputUser);
                }
            } else {
                response.put("message", "मोबाईल नंबर किंवा ईमेल सापडला नाही. (User not found.)");
                System.out.println("[Auth] Civilian login failed: User not found: " + inputUser);
            }
        } else if ("final_authority".equals(roleId) || "admin".equals(roleId)) {
            // Check Admin table first
            Optional<AdminLogin> admin = adminRepository.findByUsername(inputUser);
            if (admin.isEmpty()) {
                admin = adminRepository.findByUserId(inputUser);
            }
            
            if (admin.isPresent()) {
                System.out.println("[Auth] Admin/FA found in Admin table: " + admin.get().getName() + ", DB Pass: [" + admin.get().getPassword() + "], Input Pass: [" + inputPass + "]");
                if (admin.get().getPassword().trim().equals(inputPass)) {
                    response.put("success", true);
                    response.put("role", admin.get().getRole().toLowerCase());
                    response.put("username", admin.get().getUsername());
                    response.put("displayName", admin.get().getName());
                    response.put("userId", admin.get().getUserId());
                    response.put("name", admin.get().getName());
                    response.put("status", admin.get().getStatus());
                    System.out.println("[Auth] Admin/FA login successful: " + inputUser);
                    return response;
                }
            }

            // Secondary check in Dept table (Collector is often stored here)
            Optional<Department> dept = departmentRepository.findByHodNumber(inputUser);
            if (dept.isEmpty()) {
                dept = departmentRepository.findByUserId(inputUser);
            }
            if (dept.isPresent()) {
                System.out.println("[Auth] Collector found in Dept table: " + dept.get().getHodName() + ", DB Pass: [" + dept.get().getHodPassword() + "], Input Pass: [" + inputPass + "]");
                if (dept.get().getHodPassword() != null && dept.get().getHodPassword().trim().equals(inputPass)) {
                    response.put("success", true);
                    response.put("role", "final_authority");
                    response.put("username", dept.get().getHodNumber());
                    response.put("displayName", dept.get().getHodName());
                    response.put("userId", dept.get().getUserId() != null ? dept.get().getUserId() : "dept_" + dept.get().getId());
                    response.put("name", dept.get().getHodName());
                    response.put("status", dept.get().getStatus());
                    System.out.println("[Auth] Final Authority (Collector) login successful via Dept table: " + inputUser);
                    return response;
                }
            }

            
            response.put("message", "वापरकर्तानाव किंवा पासवर्ड चुकीचा आहे. (Invalid Credentials.)");
            System.out.println("[Auth] Admin/FA login failed: User not found or Pass mismatch: " + inputUser);
        } else if ("department".equals(roleId) || "tahsildar".equals(roleId) || "sdo".equals(roleId)) {
            // Check in Department table first
            Optional<Department> dept = departmentRepository.findByHodNumber(inputUser);
            if (dept.isEmpty()) {
                dept = departmentRepository.findByUserId(inputUser);
            }

            if (dept.isPresent()) {
                System.out.println("[Auth] Officer found in Dept table: " + dept.get().getHodName() + ", DB Pass: [" + dept.get().getHodPassword() + "], Input Pass: [" + inputPass + "]");
                if (dept.get().getHodPassword() != null && dept.get().getHodPassword().trim().equals(inputPass)) {
                    response.put("success", true);
                    response.put("role", roleId);
                    response.put("username", dept.get().getHodNumber());
                    response.put("displayName", dept.get().getHodName());
                    response.put("userId", dept.get().getUserId() != null ? dept.get().getUserId() : "dept_" + dept.get().getId());
                    response.put("name", dept.get().getHodName());
                    response.put("status", dept.get().getStatus());
                    System.out.println("[Auth] " + roleId + " login successful via Dept table: " + inputUser);
                    return response;
                }
            } else {

                System.out.println("[Auth] No exact match for HOD Num: " + inputUser + ". Checking table content...");
                departmentRepository.findAll().stream().limit(5).forEach(d -> {
                    System.out.println("[Auth] Available in DB -> HOD Num: [" + d.getHodNumber() + "], Name: [" + d.getHodName() + "]");
                });
            }

            // Secondary check in admin logins for these roles
            Optional<AdminLogin> admin = adminRepository.findByUsername(inputUser);
            if (admin.isEmpty()) {
                admin = adminRepository.findByUserId(inputUser);
            }

            if (admin.isPresent()) {
                System.out.println("[Auth] Officer found in Admin table: " + admin.get().getName() + ", DB Pass: [" + admin.get().getPassword() + "], Input Pass: [" + inputPass + "]");
                if (admin.get().getPassword().trim().equals(inputPass)) {
                    response.put("success", true);
                    response.put("role", roleId);
                    response.put("userId", admin.get().getUserId());
                    response.put("name", admin.get().getName());
                    response.put("status", admin.get().getStatus());
                    System.out.println("[Auth] " + roleId + " login successful via Admin table: " + inputUser);
                    return response;
                }
            }

            response.put("message", "अवैध लॉगिन क्रेडेन्शियल्स. (Invalid Credentials.)");
            System.out.println("[Auth] Officer login failed for: " + inputUser);
        } else {
            response.put("message", "अवैध भूमिका निवडली. (Invalid role selection.)");
        }

        return response;
    }
}
