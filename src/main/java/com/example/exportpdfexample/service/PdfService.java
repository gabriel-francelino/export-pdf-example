package com.example.exportpdfexample.service;

import com.example.exportpdfexample.model.Analysis;
import com.example.exportpdfexample.repository.AnalysisRepository;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PdfService {
    private final AnalysisRepository analysisRepository;


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

    public byte[] generatePdfFromAnalysisCustom(Analysis analysis) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);

        generateTitleOfDocument(document);
        generateAnalysisParagraph(document, analysis);

        document.close();
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] generatePdfFromAnalysisId(Long id) throws IOException {
        Optional<Analysis> analysisOptional = analysisRepository.findById(id);
        if (analysisOptional.isPresent()) {
            return generatePdfFromAnalysisCustom(analysisOptional.get());
        } else {
            throw new IllegalArgumentException("Analysis not found with ID: " + id);
        }
    }

    public byte[] generatePdfFromAllAnalysis() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);

        generateTitleOfDocument(document);

        for (Analysis analysis : analysisRepository.findAll()) {
            generateAnalysisParagraph(document, analysis);
        }

        document.close();
        return byteArrayOutputStream.toByteArray();
    }

    private void generateTitleOfDocument(Document document) throws IOException {
        Style titleStyle = new Style();
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ITALIC);
        titleStyle.setFont(font);

        Paragraph title = new Paragraph("MICROSTAMP ANALYSIS")
                .setFontSize(20)
                .setBold()
                .setFontColor(WebColors.getRGBColor("#b4894d"))
                .setTextAlignment(TextAlignment.CENTER)
                .addStyle(titleStyle);

        document.add(title);
        document.add(new Paragraph("\n"));
    }

    private void generateAnalysisParagraph(Document document, Analysis analysis) {
        Paragraph analysisTitle = new Paragraph("Title: " + analysis.getTitle())
                .setFontSize(16)
                .setBold()
                .setTextAlignment(TextAlignment.LEFT);
        document.add(analysisTitle);

        Paragraph analysisDescription = new Paragraph("Description: " + analysis.getDescription())
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT);
        document.add(analysisDescription);

        document.add(new Paragraph("\n"));
    }
}
