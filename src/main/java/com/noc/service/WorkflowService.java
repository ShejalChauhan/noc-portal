package com.noc.service;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class WorkflowService {

    // Define the workflow route: Current Role -> Next Role
    private static final Map<String, String> WORKFLOW_MAP = Map.of(
        "CIVILIAN", "FINAL_AUTHORITY",
        "FINAL_AUTHORITY", "TAHSILDAR",
        "TAHSILDAR", "DEPARTMENT",
        "DEPARTMENT", "TAHSILDAR",
        "TAHSILDAR_TO_SDO", "SDO", // Special case handled in logic if needed
        "SDO", "FINAL_AUTHORITY",
        "FINAL_AUTHORITY_TO_CIVILIAN", "CIVILIAN"
    );
    
    // Improved Map for clarity
    private static final Map<String, String> NEXT_ROLES = Map.of(
        "CIVILIAN", "FINAL_AUTHORITY",
        "FINAL_AUTHORITY", "TAHSILDAR",
        "TAHSILDAR", "DEPARTMENT",
        "DEPARTMENT", "TAHSILDAR",
        "TAHSILDAR_SDO", "SDO",
        "SDO", "FINAL_AUTHORITY",
        "FINAL_AUTHORITY_COMPLETE", "CIVILIAN"
    );

    public String getNextRole(String currentRole, String action) {
        if ("REJECT".equals(action)) {
            return "CIVILIAN"; // Rejected applications go back to civilian
        }
        
        // Custom logic for the Tahsildar dual path
        if ("TAHSILDAR".equals(currentRole)) {
            // Need a way to know if it's Department->Tahsildar or Civilian->Tahsildar
            // For now, let's simplify based on the requested route:
            // Civilian->FA->Tahsildar->Dept->Tahsildar->SDO->FA->Civilian
            return "DEPARTMENT"; // Simplified for this example
        }
        
        return NEXT_ROLES.getOrDefault(currentRole, "CIVILIAN");
    }
}
