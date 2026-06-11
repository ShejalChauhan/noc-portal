package com.noc;

import com.noc.entity.AdminLogin;
import com.noc.entity.Department;
import com.noc.entity.NocType;
import com.noc.entity.Taluka;
import com.noc.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminLoginRepository adminRepository;

    @Autowired
    private CivilianRegistrationRepository civilianRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private TalukaRepository talukaRepository;

    @Autowired
    private NocTypeRepository nocTypeRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- [NOC Seeder] Starting Database Pre-Flight Check ---");
        
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("--- [NOC Seeder] Connected to: " + conn.getMetaData().getURL() + " ---");
            
            // 1. SANITIZE INVALID DATES (Zero Date Fix)
            System.out.println("--- [NOC Seeder] Sanitizing invalid zero-dates ---");
            String[] updateQueries = {
                "UPDATE departments SET createdDateTime = '2024-01-01 00:00:00' WHERE CAST(createdDateTime AS CHAR(20)) = '0000-00-00 00:00:00'",
                "UPDATE departments SET updateDateTime = '2024-01-01 00:00:00' WHERE CAST(updateDateTime AS CHAR(20)) = '0000-00-00 00:00:00'",
                "UPDATE adminLogins SET createdDateTime = '2024-01-01 00:00:00' WHERE CAST(createdDateTime AS CHAR(20)) = '0000-00-00 00:00:00'",
                "UPDATE adminLogins SET updatedDateTime = '2024-01-01 00:00:00' WHERE CAST(updatedDateTime AS CHAR(20)) = '0000-00-00 00:00:00'",
                "UPDATE civilianRegistrations SET createDateTime = '2024-01-01 00:00:00' WHERE CAST(createDateTime AS CHAR(20)) = '0000-00-00 00:00:00'",
                "UPDATE civilianRegistrations SET updatedDateTime = '2024-01-01 00:00:00' WHERE CAST(updatedDateTime AS CHAR(20)) = '0000-00-00 00:00:00'",
                "UPDATE nocApplications SET createdDateTime = '2024-01-01 00:00:00' WHERE CAST(createdDateTime AS CHAR(20)) = '0000-00-00 00:00:00'",
                "UPDATE nocApplications SET updateDateTime = '2024-01-01 00:00:00' WHERE CAST(updateDateTime AS CHAR(20)) = '0000-00-00 00:00:00'"
            };
            for (String q : updateQueries) {
                try {
                    int rows = conn.createStatement().executeUpdate(q);
                    if (rows > 0) System.out.println("CLEANED: " + rows + " invalid dates in query: " + q);
                } catch (Exception e) { /* Ignore if table/column missing yet */ }
            }

            // 2. FORCE SCHEMA PATCH (LONGTEXT Fix)
            System.out.println("--- [NOC Seeder] Applying manual schema patches for large files ---");
            String[] patchQueries = {
                "ALTER TABLE civilianRegistrations MODIFY COLUMN identificationCertificate LONGTEXT",
                "ALTER TABLE civilianRegistrations MODIFY COLUMN profilePic LONGTEXT",
                "ALTER TABLE civilianRegistrations MODIFY COLUMN address LONGTEXT",
                "ALTER TABLE nocApplications MODIFY COLUMN nocApplicationFile LONGTEXT",
                "ALTER TABLE nocApplications MODIFY COLUMN panCard LONGTEXT",
                "ALTER TABLE nocApplications MODIFY COLUMN aadharCard LONGTEXT",
                "ALTER TABLE nocApplications MODIFY COLUMN reportFile LONGTEXT",
                "ALTER TABLE nocApplications MODIFY COLUMN initReportFile LONGTEXT",
                "ALTER TABLE nocApplications MODIFY COLUMN departmentFile LONGTEXT",
                "ALTER TABLE nocApplications MODIFY COLUMN tahildarFile LONGTEXT",
                "ALTER TABLE nocApplications MODIFY COLUMN finalTahildarFile LONGTEXT",
                "ALTER TABLE nocApplications MODIFY COLUMN SDO_final_document LONGTEXT",
                "ALTER TABLE nocApplications MODIFY COLUMN final_dsc_document LONGTEXT",
                "ALTER TABLE nocApplications MODIFY COLUMN address LONGTEXT"
            };
            for (String q : patchQueries) {
                try {
                    conn.createStatement().execute(q);
                    System.out.println("SUCCESS: " + q);
                } catch (Exception e) {
                    System.out.println("PATCH WARNING: " + e.getMessage());
                }
            }

            // 3. DROP RESTRICTIVE CONSTRAINTS (Fix for NOC Save Error)
            System.out.println("--- [NOC Seeder] Removing restrictive foreign key constraints ---");
            try {
                // First, ensure the dependent table exists so the drop check is clean
                conn.createStatement().execute("CREATE TABLE IF NOT EXISTS nocapplicationids (id INT AUTO_INCREMENT PRIMARY KEY, applicationId VARCHAR(255) UNIQUE);");
                
                // Now drop the constraint that is blocking the insert
                conn.createStatement().execute("ALTER TABLE nocApplications DROP FOREIGN KEY Foreign_Key_Application_ID_NOC_Applications");
                System.out.println("SUCCESS: Dropped Foreign_Key_Application_ID_NOC_Applications");
            } catch (Exception e) {
                System.out.println("CONSTRAINT INFO: " + e.getMessage());
            }
        }

        // 3. SEEDING LOGIC
        File sqlFile = new File("D:/NOC/u196817721_dc_ytl_noc.sql");
        if (!sqlFile.exists()) sqlFile = new File("u196817721_dc_ytl_noc.sql");

        if (sqlFile.exists()) {
            System.out.println("--- [NOC Seeder] Found SQL seed file. Running check... ---");
            seedFromSqlFile(sqlFile);
        } else {
            System.out.println("--- [NOC Seeder] SQL file not found. Running fallback. ---");
            seedFallbackData();
        }

        // 4. FINAL STATUS REPORT
        System.out.println("--- [NOC Seeder] Current Table Counts ---");
        System.out.println("--- [NOC Seeder] Admin Accounts: " + adminRepository.count());
        System.out.println("--- [NOC Seeder] Departments: " + departmentRepository.count());
        try {
            System.out.println("--- [NOC Seeder] Civilian Accounts: " + civilianRepository.count());
        } catch (Exception e) {
            System.out.println("--- [NOC Seeder] Civilian table not ready for count. ---");
        }
        System.out.println("--- [NOC Seeder] Pre-Flight Complete ---");
    }

    private void seedFromSqlFile(File sqlFile) throws Exception {
        try (Connection conn = dataSource.getConnection()) {
            ResultSet tables = conn.getMetaData().getTables(null, null, "departments", null);
            if (tables.next()) {
                ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) FROM departments");
                if (rs.next() && rs.getInt(1) > 0) {
                    System.out.println("--- [NOC Seeder] Database already contains data. Skipping SQL import. ---");
                    return;
                }
            }
        }
        System.out.println("--- [NOC Seeder] Database empty. Importing SQL script... ---");
        try (Connection conn = dataSource.getConnection()) {
            conn.createStatement().execute("SET FOREIGN_KEY_CHECKS = 0;");
            ScriptUtils.executeSqlScript(conn, new FileSystemResource(sqlFile));
            conn.createStatement().execute("SET FOREIGN_KEY_CHECKS = 1;");
            System.out.println("--- [NOC Seeder] Database successfully seeded. ---");
        }
    }

    private void seedFallbackData() {
        if (adminRepository.count() == 0) {
            AdminLogin admin = new AdminLogin();
            admin.setUserId("ADM001");
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setName("System Administrator");
            admin.setRole("Admin");
            adminRepository.save(admin);
        }
        // ... (other fallback logic remains minimal to not conflict with manual data)
    }
}
