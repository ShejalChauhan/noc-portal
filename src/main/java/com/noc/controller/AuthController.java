package com.noc.controller;

import com.noc.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173") // Allow React Dev Server CORS requests
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        String roleId = request.get("roleId");
        String username = request.get("username");
        String password = request.get("password");

        if (roleId == null || username == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of(
                "success", false,
                "message", "कृपया सर्व आवश्यक क्रेडेन्शियल्स प्रविष्ट करा. (Please provide all credentials.)"
            ));
        }

        Map<String, Object> result = authService.authenticate(roleId, username, password);
        if (Boolean.TRUE.equals(result.get("success"))) {
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.status(401).body(result);
    }
}
