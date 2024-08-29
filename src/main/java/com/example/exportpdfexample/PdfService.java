package com.example.exportpdfexample;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PdfService {
    private final AnalysisRepository analysisRepository;

    public Analysis save(Analysis analysis) {
        return analysisRepository.save(analysis);
    }

    public byte[] generatePdfFromAnalysis(Analysis analysis) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Cria um PdfWriter para escrever no ByteArrayOutputStream
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);

        // Cria um PdfDocument para usar o PdfWriter
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);

        // Cria um Document para adicionar conteúdo ao PdfDocument
        Document document = new Document(pdfDocument);

        // Adiciona o título e o conteúdo do objeto Analysis ao documento
        document.add(new Paragraph("Title: " + analysis.getTitle()));
        document.add(new Paragraph("Content: " + analysis.getDescription()));

        // Fecha o documento
        document.close();

        // Retorna os bytes contendo os dados do PDF
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] generatePdfFromAnalysisId(Long id) {
        Optional<Analysis> analysisOptional = analysisRepository.findById(id);
        if (analysisOptional.isPresent()) {
            return generatePdfFromAnalysis(analysisOptional.get());
        } else {
            throw new IllegalArgumentException("Analysis not found with ID: " + id);
        }
    }
}
