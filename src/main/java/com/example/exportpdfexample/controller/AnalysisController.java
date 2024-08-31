package com.example.exportpdfexample.controller;

import com.example.exportpdfexample.model.Analysis;
import com.example.exportpdfexample.service.AnalysisService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/analysis")
public class AnalysisController {
    private final AnalysisService analysisService;

    @PostMapping
    public ResponseEntity<Analysis> saveAnalysis(@RequestBody Analysis analysis) {
        Analysis savedAnalysis = analysisService.save(analysis);
        URI location = URI.create(String.format("/api/analysis/%d", savedAnalysis.getId()));

        return ResponseEntity.created(location).body(savedAnalysis);
    }

    @GetMapping
    public ResponseEntity<List<Analysis>> findAll() {
        List<Analysis> analyses = analysisService.findAll();
        return ResponseEntity.ok(analyses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Analysis> findById(@PathVariable Long id) {
        Analysis analysis = analysisService.findById(id);
        return ResponseEntity.ok(analysis);
    }

}
