package com.noc.controller;

import com.noc.entity.NocType;
import com.noc.entity.Taluka;
import com.noc.entity.Department;
import com.noc.repository.NocTypeRepository;
import com.noc.repository.TalukaRepository;
import com.noc.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/master")
@CrossOrigin(origins = "http://localhost:5173")
public class MasterDataController {

    @Autowired
    private NocTypeRepository nocTypeRepository;

    @Autowired
    private TalukaRepository talukaRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/noc-types")
    public ResponseEntity<List<NocType>> getActiveNocTypes() {
        List<NocType> activeTypes = nocTypeRepository.findByStatus("Active");
        return ResponseEntity.ok(activeTypes);
    }

    @GetMapping("/talukas")
    public ResponseEntity<List<Taluka>> getActiveTalukas() {
        List<Taluka> talukas = talukaRepository.findDistinctByStatus("Active");
        return ResponseEntity.ok(talukas);
    }

    @GetMapping("/villages")
    public ResponseEntity<List<Taluka>> getVillagesByTaluka(@RequestParam String taluka) {
        List<Taluka> villages = talukaRepository.findByTalukaAndStatus(taluka, "Active");
        return ResponseEntity.ok(villages);
    }

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getActiveDepartments() {
        List<Department> activeDepts = departmentRepository.findByStatus("Active");
        return ResponseEntity.ok(activeDepts);
    }
}
