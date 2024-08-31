package com.example.exportpdfexample.controller;

import com.example.exportpdfexample.model.Analysis;
import com.example.exportpdfexample.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {
    private final PdfService pdfGenerationService;

    public PdfController(PdfService pdfGenerationService) {
        this.pdfGenerationService = pdfGenerationService;
    }


    @PostMapping("/generate")
    public ResponseEntity<byte[]> generatePdf(@RequestBody Analysis analysis) {
        byte[] pdfBytes = pdfGenerationService.generatePdfFromAnalysis(analysis);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "analysis.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> generatePdfById(@PathVariable Long id) throws IOException {
        byte[] pdfBytes = pdfGenerationService.generatePdfFromAnalysisId(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "analysis_" + id + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<byte[]> generatePdfFromAllAnalysis() throws IOException {
        byte[] pdfBytes = pdfGenerationService.generatePdfFromAllAnalysis();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "all_analysis.pdf");

//        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}
