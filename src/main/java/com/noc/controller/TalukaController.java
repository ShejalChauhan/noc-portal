package com.noc.controller;

import com.noc.entity.Taluka;
import com.noc.repository.TalukaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/taluka")
@CrossOrigin(origins = "http://localhost:5173")
public class TalukaController {

    @Autowired
    private TalukaRepository talukaRepository;

    @GetMapping
    public ResponseEntity<List<Taluka>> getActiveTalukas() {
        // Reuse the existing repository method that filters by 'Active' status
        List<Taluka> talukas = talukaRepository.findDistinctByStatus("Active");
        return ResponseEntity.ok(talukas);
    }
}
