package com.example.exportpdfexample;

import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
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

    public byte[] generatePdfFromAnalysisCustom(Analysis analysis) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);


        // Adiciona o título centralizado no topo
        Paragraph title = new Paragraph("MICROSTAMP ANALYSIS")
                .setFontSize(20)
                .setBold()
//                .setFontFamily("Arial")
                // cor #b4894d em rgb
                .setFontColor(WebColors.getRGBColor("#b4894d"))
                .setTextAlignment(TextAlignment.CENTER);
        document.add(title);

        // Adiciona um espaçamento após o título
        document.add(new Paragraph("\n"));

        // Adiciona o título da análise
        Paragraph analysisTitle = new Paragraph("Title: " + analysis.getTitle())
                .setFontSize(16)
//                .setFontFamily("Poppins")
                .setBold()
                .setTextAlignment(TextAlignment.LEFT);
        document.add(analysisTitle);

        // Adiciona a descrição da análise
        Paragraph analysisDescription = new Paragraph("Description: " + analysis.getDescription())
                .setFontSize(12)
//                .setFontFamily("Poppins")
                .setTextAlignment(TextAlignment.LEFT);
        document.add(analysisDescription);

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
}
