package com.example.exportpdfexample;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {
    private final PdfService pdfGenerationService;

    public PdfController(PdfService pdfGenerationService) {
        this.pdfGenerationService = pdfGenerationService;
    }

    @PostMapping("/save")
    public ResponseEntity<Analysis> saveAnalysis(@RequestBody Analysis analysis) {
        Analysis savedAnalysis = pdfGenerationService.save(analysis);
        return new ResponseEntity<>(savedAnalysis, HttpStatus.CREATED);
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generatePdf(@RequestBody Analysis analysis) {
        byte[] pdfBytes = pdfGenerationService.generatePdfFromAnalysis(analysis);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "analysis.pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/generate/{id}")
    public ResponseEntity<byte[]> generatePdfById(@PathVariable Long id) {
        byte[] pdfBytes = pdfGenerationService.generatePdfFromAnalysisId(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "analysis_" + id + ".pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
