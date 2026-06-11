package com.noc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
public class NocBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(NocBackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner schemaPatch(DataSource dataSource) {
        return args -> {
            System.out.println("*******************************************************");
            System.out.println("!!! FORCING DATABASE SCHEMA PATCH FOR LARGE FILES !!!");
            try (Connection conn = dataSource.getConnection()) {
                String[] queries = {
                    "ALTER TABLE civilianRegistrations MODIFY COLUMN identificationCertificate LONGTEXT",
                    "ALTER TABLE civilianRegistrations MODIFY COLUMN profilePic LONGTEXT",
                    "ALTER TABLE nocApplications MODIFY COLUMN identificationCertificate LONGTEXT",
                    "ALTER TABLE nocApplications MODIFY COLUMN nocApplicationFile LONGTEXT",
                    "ALTER TABLE nocApplications MODIFY COLUMN panCard LONGTEXT",
                    "ALTER TABLE nocApplications MODIFY COLUMN aadharCard LONGTEXT",
                    "ALTER TABLE nocApplications MODIFY COLUMN reportFile LONGTEXT",
                    "ALTER TABLE nocApplications MODIFY COLUMN initReportFile LONGTEXT",
                    "ALTER TABLE nocApplications MODIFY COLUMN departmentFile LONGTEXT",
                    "ALTER TABLE nocApplications MODIFY COLUMN tahildarFile LONGTEXT",
                    "ALTER TABLE nocApplications MODIFY COLUMN finalTahildarFile LONGTEXT",
                    "ALTER TABLE nocApplications MODIFY COLUMN SDO_final_document LONGTEXT",
                    "ALTER TABLE nocApplications MODIFY COLUMN final_dsc_document LONGTEXT"
                };

                for (String query : queries) {
                    try {
                        conn.createStatement().execute(query);
                        System.out.println("SUCCESS: " + query);
                    } catch (Exception e) {
                        System.err.println("WARNING: Failed patch query: " + query + " - " + e.getMessage());
                    }
                }
                System.out.println("!!! DATABASE SCHEMA PATCH COMPLETED !!!");
            } catch (Exception e) {
                System.err.println("ERROR: Could not apply schema patch: " + e.getMessage());
            }
            System.out.println("*******************************************************");
        };
    }
}
