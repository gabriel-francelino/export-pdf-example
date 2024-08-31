package com.example.exportpdfexample.service;

import com.example.exportpdfexample.model.Analysis;
import com.example.exportpdfexample.repository.AnalysisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnalysisService {
    private final AnalysisRepository analysisRepository;

    public Analysis save(Analysis analysis) {
        return analysisRepository.save(analysis);
    }

    public List<Analysis> findAll() {
        return analysisRepository.findAll();
    }

    public Analysis findById(Long id) {
        return analysisRepository.findById(id).orElse(new Analysis());
    }
}
