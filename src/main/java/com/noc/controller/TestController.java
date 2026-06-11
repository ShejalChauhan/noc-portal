package com.noc.controller;

import com.noc.entity.AdminLogin;
import com.noc.entity.Department;
import com.noc.repository.AdminLoginRepository;
import com.noc.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @Autowired
    private AdminLoginRepository adminLoginRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/dump")
    public Map<String, Object> dump() {
        Map<String, Object> result = new HashMap<>();
        List<AdminLogin> admins = adminLoginRepository.findAll();
        List<Department> departments = departmentRepository.findAll();
        result.put("admins", admins);
        result.put("departments", departments);
        return result;
    }
}